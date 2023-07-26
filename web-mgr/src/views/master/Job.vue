<template>
  <div>

    <div class="container">

      <div class="handle-box">
        <el-button type="danger" icon="el-icon-plus" @click="handleAdd">新增</el-button>
        <el-button type="default" icon="el-icon-refresh" @click="handleRefresh">刷新</el-button>
      </div>

      <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
      >
        <el-table-column prop="id" label="ID" width="210"></el-table-column>
        <el-table-column prop="title" label="名称"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="beanName" label="执行">
          <template v-slot="scope">
            {{ scope.row.beanName }}.{{ scope.row.methodName }}({{ scope.row.methodParam }})
          </template>
        </el-table-column>
        <el-table-column prop="cron" label="规则"></el-table-column>
        <el-table-column prop="status" label="状态" width="210" align="center">
          <template v-slot="scope">
            <el-button
                type="warning" plain size="mini"
                icon="el-icon-video-play"
                @click="changeStatus(scope.$index, scope.row)"
                v-if="scope.row.status === 0"
            >启动
            </el-button>
            <el-button
                type="danger" plain size="mini"
                icon="el-icon-video-pause"
                @click="changeStatus(scope.$index, scope.row)"
                v-if="scope.row.status === 1"
            >停止
            </el-button>
            <el-button
                type="success" plain size="mini"
                icon="el-icon-caret-right"
                @click="executeNow(scope.$index, scope.row)"
            >立即执行
            </el-button>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="130" align="center">
          <template v-slot="scope">
            <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
            >编辑
            </el-button>
            <el-button
                type="text"
                icon="el-icon-delete"
                class="red"
                @click="handleDelete(scope.$index, scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>

      </el-table>

      <div class="pagination">
        <el-pagination
            background
            layout="total, prev, pager, next"
            :current-page="query.page"
            :page-size="query.size"
            :total="total"
            @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>

    <!-- 弹出框-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="23%">

      <el-form ref="form" :model="form" label-width="81px">

        <el-form-item label="任务名称">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="任务简介">
          <el-input v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="执行类名">
          <el-input v-model="form.beanName"></el-input>
        </el-form-item>
        <el-form-item label="执行方法">
          <el-input v-model="form.methodName"></el-input>
        </el-form-item>
        <el-form-item label="方法参数">
          <el-input v-model="form.methodParam"></el-input>
        </el-form-item>
        <el-form-item label="执行规则">
          <el-input v-model="form.cron"></el-input>
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>

    </el-dialog>

  </div>
</template>

<script>
import {JobAdd, JobStart, JobDel, JobEdit, JobExecute, JobList} from '@/utils/Api';

export default {
  name: 'JobList',
  data() {
    return {
      query: {
        searchField: '',
        searchContent: '',
        page: 1,
        size: 10
      },
      tableData: [],
      dialogVisible: false,
      dialogTitle: '',
      total: 0,
      form: {},
      idx: -1,
      id: -1,
    };
  },
  created() {
    this.listData();
  },
  methods: {
    // 枚举
    listData() {
      JobList(this.query).then(res => {
        if (res.code === 200) {
          this.tableData = res.content.list;
          this.total = res.content.count;
        } else {
          this.$message.error('查看失败,' + res.msg);
        }
      });
    },
    // 分页导航
    handlePageChange(val) {
      this.$set(this.query, 'page', val);
      this.listData();
    },
    //刷新
    handleRefresh() {
      //window.location.reload();
      this.$router.go(0);
    },
    //增加
    handleAdd() {
      this.dialogVisible = true;
      this.dialogTitle = '增加';
      this.form = {};
    },
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      this.form = row;
      this.dialogVisible = true;
      this.dialogTitle = '编辑';
    },
    //提交
    submitForm() {
      if ('增加' === this.dialogTitle) {
        JobAdd(this.form).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false;
            this.$message.success(this.dialogTitle + '成功');
            this.listData();
          } else {
            this.$message.error(this.dialogTitle + '失败,' + res.msg);
          }
        });
      } else {
        JobEdit(this.form).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false;
            this.$message.success(this.dialogTitle + '成功');
            this.listData();
          } else {
            this.$message.error(this.dialogTitle + '失败,' + res.msg);
          }
        });
      }
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        JobDel({'id': row.id}).then(res => {
          if (res.code === 200) {
            this.$message.success('删除成功');
            this.$set(this.query, 'page', 1);
            this.listData();
          } else {
            this.$message.error('删除失败,' + res.msg);
          }
        });
      });
    },
    //立即执行
    executeNow(index, row) {
      JobExecute(row.id).then(res => {
        if (res.code === 200) {
          this.$message.success('执行成功');
        } else {
          this.$message.error('执行失败,' + res.msg);
        }
      });
    },
    //改变状态
    changeStatus(index, row) {
      JobStart(row.id).then(res => {
        if (res.code === 200) {
          this.listData();
        } else {
          this.$message.error('执行失败,' + res.msg);
        }
      });
    },

  }

};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #ff0000;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
