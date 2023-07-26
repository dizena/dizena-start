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
          header-cell-class-name="table-header"
          @expand-change="expandChange"
      >
        <el-table-column type="expand">
          <template v-slot="scope" class="child-table">
            <el-table v-if="scope.row.children.length > 0" :data="scope.row.children" border>

              <el-table-column prop="id" label="ID"></el-table-column>
              <el-table-column prop="title" label="名称"></el-table-column>
              <el-table-column prop="uri" label="URI"></el-table-column>
              <el-table-column prop="sort" label="序号"></el-table-column>
              <el-table-column prop="icon" label="图标">
                <template v-slot="scope">
                  <i :class="scope.row.icon"></i>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="180" align="center">
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
            <div v-else style="text-align: center;color: orangered;">
              子表暂无数据
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column prop="title" label="名称"></el-table-column>
        <el-table-column prop="uri" label="URI"></el-table-column>
        <el-table-column prop="sort" label="序号"></el-table-column>
        <el-table-column prop="icon" label="图标">
          <template v-slot="scope">
            <i :class="scope.row.icon"></i>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
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
        <el-form-item label="名称">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="URI">
          <el-input v-model="form.uri"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon"></el-input>
        </el-form-item>
        <el-form-item label="序号">
          <el-input v-model="form.sort"></el-input>
        </el-form-item>
        <el-form-item label="级别">
          <el-select v-model="form.level" @change="handleLevelChange">
            <el-option key="1" label="一级资源" value="1"></el-option>
            <el-option key="2" label="二级资源" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父级">
          <el-select v-model="form.pid">
            <el-option v-for="vo in parent" :key="vo.id" :label="vo.title" :value="vo.id"></el-option>
          </el-select>
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
import {ResList, ResAdd, ResEdit, ResDel, ResLevel} from '@/utils/Api';

export default {
  name: 'ResList',
  data() {
    return {
      query: {
        sort: 'sort',
        dire: 'ASC',
        page: 1,
        size: 10
      },
      tableData: [],
      dialogTitle: '',
      dialogVisible: false,
      total: 0,
      form: {},
      parent: [],
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
      ResList(this.query).then(res => {
        if (res.code === 200) {
          this.tableData = res.content.list;
          this.total = res.content.count;
        } else {
          this.$message.error('查看失败,' + res.msg);
        }
      });
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        ResDel(row.id).then(res => {
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
    //刷新
    handleRefresh() {
      window.location.reload();
      //this.$router.go(0);
    },
    //增加操作
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
      this.handleLevelChange();
    },
    // 保存编辑
    submitForm() {
      if ('增加' === this.dialogTitle) {
        ResAdd(this.form).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false;
            this.listData();
            this.$message.success(this.dialogTitle + '成功');
          } else {
            this.$message.error(this.dialogTitle + '失败');
          }
        });
      } else {
        ResEdit(this.form).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false;
            this.listData();
            this.$message.success(this.dialogTitle + '成功');
          } else {
            this.$message.error(this.dialogTitle + '失败');
          }
        });
      }
    },
    // 分页导航
    handlePageChange(val) {
      this.$set(this.query, 'page', val);
      this.listData();
    },
    //展开
    expandChange(row) {
    },
    //级别改变
    handleLevelChange() {
      ResLevel(this.form.level).then(res => {
        if (res.code === 200) {
          this.parent = res.content;
        } else {
          this.$message.error('加载父级资源失败,' + res.msg);
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

.el-table__expanded-cell .el-table--small{
  margin-left: 45px;
  width: 95%;
}
</style>
