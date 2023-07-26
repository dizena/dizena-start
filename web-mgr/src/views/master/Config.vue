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
        <el-table-column prop="id" label="ID" width="185"></el-table-column>
        <el-table-column prop="key" label="参数名"  width="185"></el-table-column>
        <el-table-column prop="value" label="参数值"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="181">
          <template v-slot="scope">
            {{ fmtTimeLong(scope.row.createTime) }}
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

        <el-form-item label="参数名">
          <el-input v-model="form.key"></el-input>
        </el-form-item>
        <el-form-item label="参数值">
          <el-input v-model="form.value"></el-input>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.description"></el-input>
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
import {ConfigList, ConfigDel, ConfigEdit, ConfigAdd} from '@/utils/Api';
import {fullTime} from "@/utils/DateUtil";

export default {
  name: 'BaseConfig',
  data() {
    return {
      query: {
        searchField: '',
        searchContent: '',
        dire: "ASC",
        sort: "createTime",
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
      ConfigList(this.query).then(res => {
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
        ConfigAdd(this.form).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false;
            this.$message.success(this.dialogTitle + '成功');
            this.listData();
          } else {
            this.$message.error(this.dialogTitle + '失败,' + res.msg);
          }
        });
      } else {
        ConfigEdit(this.form).then(res => {
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
        //console.log(sessionStorage.getItem('AccessToken'))
        ConfigDel(row.key).then(res => {
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
    //格式化时间
    fmtTimeLong: function (longTs) {
      return fullTime(longTs);
    },
    //

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
