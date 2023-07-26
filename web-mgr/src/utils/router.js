import Vue from 'vue';
import Router from 'vue-router';
//views
import Home from '../views/common/Home.vue'

import Login from '../views/guest/Login.vue'
import Index from '../views/guest/Info.vue'
import Password from '../views/guest/Password.vue'
import V403 from '../views/guest/403.vue'
import V404 from '../views/guest/404.vue'

import User from '../views/master/User.vue'
import Role from '../views/master/Role.vue'
import Res from '../views/master/Res.vue'
import Config from '../views/master/Config.vue'
import Job from '../views/master/Job.vue'
//end

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/Index'
        },
        {
            path: '/Login',
            meta: {title: '登录页面'},
            component: Login,
        },
        {
            path: '*',
            redirect: '/404'
        },
        {
            path: '/',
            component: Home,
            meta: {title: '自述文件'},
            children: [
                {
                    path: '/Index',
                    component: Index,
                    meta: {title: '系统首页'}
                },
                {
                    path: '/Password',
                    component: Password,
                    meta: {title: '修改密码'}
                },
                {
                    path: '/User',
                    component: User,
                    meta: {title: '用户管理'}
                },
                {
                    path: '/Role',
                    component: Role,
                    meta: {title: '角色管理'}
                },
                {
                    path: '/Res',
                    component: Res,
                    meta: {title: '资源管理'}
                },
                {
                    path: '/Config',
                    component: Config,
                    meta: {title: '配置管理'}
                },
                {
                    path: '/Job',
                    component: Job,
                    meta: {title: '任务管理'}
                },
                {
                    path: '/404',
                    component: V404,
                    meta: {title: '404'}
                },
                {
                    path: '/403',
                    component: V403,
                    meta: {title: '403'}
                }
            ]
        }

    ]
});
