import request from "@/utils/request";

//游客接口
export const AuthLogin = (json) => postJson('/guest/first/login-account', json);
export const GetConfig = (key) => get('/guest/first/get-config/' + key);
//用户中心
export const LoginUserInfo = () => get('/center/first/info');
export const LoginUserUpdatePassword = (form) => postForm('/center/first/modify-pwd', form);
export const LoginUserUpdateInfo = (json) => postJson('/center/first/modify-info', json);
export const LoginUserMenu = () => get('/center/first/menu');

//用户接口
export const UserAdd = (form) => postForm('/admin/master/user/add', form);
export const UserDel = (id) => get('/admin/master/user/del/' + id);
export const UserEdit = (form) => postForm('/admin/master/user/edit', form);
export const UserList = (json) => postJson('/admin/master/user/list', json);
export const UserLock = (id) => get('/admin/master/user/lock/' + id);
//角色接口
export const RoleAdd = (form) => postForm('/admin/master/role/add', form);
export const RoleDel = (id) => get('/admin/master/role/del/' + id);
export const RoleEdit = (form) => postForm('/admin/master/role/edit', form);
export const RoleList = (form) => postForm('/admin/master/role/list', form);
//资源接口
export const ResAdd = (form) => postForm('/admin/master/res/add', form);
export const ResDel = (id) => get('/admin/master/res/del/' + id);
export const ResEdit = (form) => postForm('/admin/master/res/edit', form);
export const ResList = (form) => postForm('/admin/master/res/list', form);
export const ResLevel = (v) => get('/admin/master/res/level/' + v);
export const ResView = () => get('/admin/master/res/view');
//config
export const ConfigAdd = (form) => postForm('/admin/master/config/add', form);
export const ConfigDel = (id) => get('/admin/master/config/del/' + id);
export const ConfigEdit = (form) => postForm('/admin/master/config/edit', form);
export const ConfigList = (form) => postForm('/admin/master/config/list', form);
//Job
export const JobAdd = (form) => postForm('/admin/master/job/add', form);
export const JobDel = (id) => get('/admin/master/job/del/' + id);
export const JobEdit = (form) => postForm('/admin/master/job/edit', form);
export const JobList = (form) => postForm('/admin/master/job/list', form);
export const JobStart = (id) => get('/admin/master/job/start/' + id);
export const JobExecute = (id) => get('/admin/master/job/execute/' + id);


export const postJson = function (url, json) {
    let config = {
        headers: {'Content-Type': 'application/json;charset=UTF-8'}
    };
    let AccessToken = sessionStorage.getItem('AccessToken');
    if (AccessToken) {
        config.headers.AccessToken = AccessToken;
    }
    return request.post(url, json, config);
}

export const postForm = function (url, form) {
    let config = {
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    };
    let AccessToken = sessionStorage.getItem('AccessToken');
    if (AccessToken) {
        config.headers.AccessToken = AccessToken;
    }
    let formData = new FormData();
    for (let key in form) {
        formData.append(key, form[key]);
    }
    return request.post(url, formData, config);
}

export const uploadFile = function (url, formData) {
    let config = {
        headers: {'Content-Type': 'multipart/form-data;charset=UTF-8'}
    };
    let AccessToken = sessionStorage.getItem('AccessToken');
    if (AccessToken) {
        config.headers.AccessToken = AccessToken;
    }
    return request.post(url, formData, config);
}

export const get = function (url) {
    let config = {
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    };
    let AccessToken = sessionStorage.getItem('AccessToken');
    if (AccessToken) {
        config.headers.AccessToken = AccessToken;
    }
    return request.get(url, config);
}