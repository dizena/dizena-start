<template>
  <div>

    <div class="container">

      <div class="handle-box">
        <el-select v-model="platform" placeholder="平台" class="handle-select mr10">
          <el-option key="11" label="打工人同盟" value="wxApp-wxd20b39a04b004966"></el-option>
          <el-option key="12" label="旅游者同盟" value="wxApp-wx301b2d0a22af4198"></el-option>
          <el-option key="13" label="搭友圈" value="wxApp-wx26bb78e4aea449df"></el-option>
          <el-option key="14" label="民大" value="MinDa"></el-option>
          <el-option key="15" label="管理" value="mgr"></el-option>
        </el-select>
        <el-select v-model="query.searchField" placeholder="检索方式" class="handle-select mr10">
          <el-option key="21" label="账户" value="account"></el-option>
          <el-option key="22" label="昵称" value="nickname"></el-option>
        </el-select>
        <el-input v-model="query.searchContent" placeholder="检索内容" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button type="danger" icon="el-icon-plus" @click="handleAdd">新增</el-button>
        <el-button type="default" icon="el-icon-refresh" @click="handleRefresh">刷新</el-button>
      </div>

      <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="account" label="账户" width="281"></el-table-column>
        <el-table-column prop="nickname" label="昵称" width="131"></el-table-column>
        <el-table-column prop="platform" label="来源" width="131"></el-table-column>

        <el-table-column prop="mobile" label="信息">
          <template v-slot="scope">
            {{ scope.row.mobile }}
            {{ scope.row.email }} {{ scope.row.stuEmail }}
          </template>
        </el-table-column>

        <el-table-column prop="roles" label="角色" width="100"></el-table-column>
        <el-table-column prop="auths" label="权限" width="100"></el-table-column>
        <el-table-column label="状态" align="center" width="100">
          <template v-slot="scope">
            {{ scope.row.locked === 1 ? '正常' : '锁定' }}
          </template>
        </el-table-column>

        <el-table-column prop="createTime" label="注册时间" width="180">
          <template v-slot="scope">
            {{ fmtTimeLong(scope.row.createTime) }}
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
            <el-button
                type="text"
                icon="el-icon-unlock"
                class="lock"
                @click="handleLock(scope.$index, scope.row)"
                v-if="scope.row.locked === 1"
            >禁用
            </el-button>
            <el-button
                type="text"
                icon="el-icon-lock"
                class="lock"
                @click="handleLock(scope.$index, scope.row)"
                v-if="scope.row.locked != 1"
            >解禁
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

    <!-- 增加弹出框-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="23%">
      <el-form ref="form" :model="form" label-width="81px">

        <el-form-item label="昵称">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <template v-if="dialogTitle==='增加'">
          <el-form-item label="账户">
            <el-input v-model="form.account"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.passwd"></el-input>
          </el-form-item>
        </template>
        <el-form-item label="角色">
          <el-input v-model="form.roles"></el-input>
        </el-form-item>
        <el-form-item label="权限">
          <el-input v-model="form.auths"></el-input>
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
import {UserList, UserAdd, UserDel, UserEdit, UserLock} from '@/utils/Api';
import {leaveTime} from '@/utils/DateUtil';

export default {
  name: 'UserList',
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
      platform:'',
      tableData: [],
      multipleSelection: [],
      delList: [],
      dialogVisible: false,
      dialogTitle: '',
      total: 0,
      form: {},
      idx: -1,
      id: -1
    };
  },
  created() {
    this.listData();
  },
  methods: {
    // 枚举
    listData() {
      let conditions = [{
        k:'platform',
        t:'str',
        v:this.platform,
        c:'='
      }]
      this.query.conditions = conditions;
      UserList(this.query).then(res => {
        if (res.code === 200) {
          this.tableData = res.content.list;
          this.total = res.content.count;
        } else {
          this.$message.error('查看失败,' + res.msg);
        }
      });
    },
    // 搜索
    handleSearch() {
      this.$set(this.query, 'page', 1);
      this.listData();
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('确定要删除吗？', '提示', {
        type: 'warning'
      }).then(() => {
        UserDel(row.id).then(res => {
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
    //锁定
    handleLock(index, row) {
      UserLock(row.id).then(res => {
        if (res.code === 200) {
          this.listData();
        }
      });
    },
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //多删
    delAllSelection() {
      this.$confirm('确定选中的删除吗？', '提示', {type: 'warning'}).then(() => {
        const length = this.multipleSelection.length;
        this.delList = this.delList.concat(this.multipleSelection);
        for (let i = 0; i < length; i++) {
          let id = this.multipleSelection[i].id;
          if (id !== '1') {

          }
        }
        this.multipleSelection = [];
        this.$router.go(0);
      });
    },
    //刷新
    handleRefresh() {
      //window.location.reload();
      this.$router.go(0);
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
    },
    // 保存
    submitForm() {
      if ('增加' === this.dialogTitle) {
        UserAdd(this.form).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false;
            this.$message.success(this.dialogTitle + '成功');
            this.listData();
          } else {
            this.$message.error(this.dialogTitle + '失败,' + res.msg);
          }
        });
      } else {
        UserEdit(this.form).then(res => {
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
    // 分页导航
    handlePageChange(val) {
      this.$set(this.query, 'page', val);
      this.listData();
    },
    //格式化时间
    fmtTimeLong: function (longTs) {
      return leaveTime(longTs);
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #ff0000;
}

.lock {
  color: darkmagenta;
}

.mr10 {
  margin-right: 10px;
}

</style>
