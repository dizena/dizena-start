<template>
  <div class="sidebar">
    <el-menu
        class="sidebar-el-menu"
        :default-active="onRoutes"
        :collapse="collapse"
        background-color="#324157"
        text-color="#bfcbd9"
        active-text-color="#20a0ff"
        unique-opened
        router
    >
      <template v-for="(item,index) in items">

        <template v-if="item.subs">
          <el-submenu :index="item.index" :key="item.title">

            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.title }}</span>
            </template>

            <template v-for="(subItem,index) in item.subs">

              <el-submenu
                  v-if="subItem.subs"
                  :index="subItem.index"
                  :key="subItem.title"
              >
                <i :class="subItem.icon"></i>
                <template slot="title">{{ subItem.title }}</template>
                <el-menu-item
                    v-for="(threeItem,i) in subItem.subs"
                    :key="threeItem.title"
                    :index="threeItem.index"
                >
                  <i :class="threeItem.icon"></i>{{ threeItem.title }}
                </el-menu-item>
              </el-submenu>

              <el-menu-item
                  v-else
                  :index="subItem.index"
                  :key="subItem.title"
              >
                <i :class="subItem.icon"></i>{{ subItem.title }}
              </el-menu-item>

            </template>
          </el-submenu>
        </template>

        <template v-else>
          <el-menu-item :index="item.index" :key="item.title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </template>

      </template>
    </el-menu>
  </div>
</template>

<script>
import bus from '../common/bus';
import {LoginUserMenu} from "@/utils/Api";

export default {
  data() {
    return {
      collapse: false,
      items: [
        {
          icon: 'el-icon-lx-home',
          index: 'Index',
          title: '系统首页'
        },
        {
          icon: 'el-icon-lx-settings',
          index: '2',
          title: '系统管理',
          subs: [
            {
              title: '用户管理',
              index: 'User'
            }, {
              title: '角色管理',
              index: 'Role'
            }, {
              title: '资源管理',
              index: 'Res'
            }
          ]
        },
      ]
    };
  },
  computed: {
    onRoutes() {
      return this.$route.path.replace('/', '');
    }
  },
  created() {
    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg;
      bus.$emit('collapse-content', msg);
    });
    //初始化数据
    this.initMenu();
  },
  methods: {
    initMenu() {
      LoginUserMenu().then(res => {
        if (res.code === 200) {
          this.items = res.content;
        } else {
          this.$message.error("获取菜单错误," + res.msg);
        }
      });
    }
  }
};
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar > ul {
  height: 100%;
}
</style>
