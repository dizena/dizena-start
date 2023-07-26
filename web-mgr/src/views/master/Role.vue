<template>
  <div>

    <div class="container">

      <div class="handle-box">
        <el-button
            type="primary"
            icon="el-icon-delete"
            class="handle-del mr10"
            @click="delAllSelection"
        >批量删除
        </el-button>
        <el-select v-model="query.searchField" placeholder="检索方式" class="handle-select mr10">
          <el-option key="1" label="角色" value="role"></el-option>
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
        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column prop="role" label="角色"></el-table-column>
        <el-table-column prop="resIds" label="资源">
          <template v-slot="scope">
            <el-tree :data="scope.row.menu" node-key="id" ref="tree" :default-expand-all="true"></el-tree>
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

    <!-- 编辑弹出框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="23%">
      <el-form ref="form" :model="form" label-width="81px">
        <el-form-item label="名称">
          <el-input v-model="form.role"></el-input>
        </el-form-item>

        <el-form-item label="资源">
          <el-tree :data="treeData" show-checkbox node-key="id" ref="tree" :default-expand-all="true"
                   :allow-drop="allowDrop"></el-tree>
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
import {RoleList, RoleEdit, RoleAdd, RoleDel, ResView} from '@/utils/Api';

export default {
  name: 'RoleList',
  data() {
    return {
      query: {
        searchField: '',
        searchContent: '',
        page: 1,
        size: 10
      },
      tableData: [],
      multipleSelection: [],
      delList: [],
      dialogTitle: '',
      dialogVisible: false,
      total: 0,
      form: {},
      idx: -1,
      id: -1,
      treeData: [],
      defaultProps: {
        // 用于修改节点指定标签的属性值
        children: 'children',
        label: 'label'
      }
    };
  },
  created() {
    this.listData();
    this.getTreeData();
  },
  methods: {
    // 枚举
    listData() {
      RoleList(this.query).then(res => {
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
        RoleDel(row.id).then(res => {
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
    handleRefresh(){
      //window.location.reload();
      this.$router.go(0);
    },
    //增加
    handleAdd() {
      this.dialogVisible = true;
      this.dialogTitle = '增加';
      this.$refs.tree.setCheckedKeys([]);
      this.form = {};
    },
    // 编辑
    handleEdit(index, row) {
      this.idx = index;
      this.form = row;
      this.dialogVisible = true;
      this.dialogTitle = '编辑';
      this.$refs.tree.setCheckedKeys(row.resIds);
    },
    // 保存
    submitForm() {
      if ('增加' === this.dialogTitle) {
        let param = {
          'role': this.form.role,
          'resIds': this.$refs.tree.getHalfCheckedKeys().concat(this.$refs.tree.getCheckedKeys())
        }
        RoleAdd(param).then(res => {
          if (res.code === 200) {
            this.dialogVisible = false;
            this.$message.success(this.dialogTitle + '成功');
            this.listData();
          } else {
            this.$message.error(this.dialogTitle + '失败,' + res.msg);
          }
        });
      } else {
        let param = {
          'id': this.form.id,
          'role': this.form.role,
          'resIds': this.$refs.tree.getHalfCheckedKeys().concat(this.$refs.tree.getCheckedKeys())
        }
        RoleEdit(param).then(res => {
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
    //资源树结构下拉
    allowDrop(draggingNode, dropNode, type) {
      if (draggingNode.parrent.id === dropNode.parrent.id) {
        return type !== 'next';
      } else {
        return true;
      }
    },
    //资源获取
    getTreeData() {
      ResView().then(res => {
        if (res.code === 200) {
          this.treeData = res.content;
        } else {
          this.$message.error('获取失败,' + res.msg);
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

.mr10 {
  margin-right: 10px;
}

</style>
