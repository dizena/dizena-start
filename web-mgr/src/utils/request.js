import axios from 'axios';
import BASE_URL from "../../config";

const service = axios.create({
    baseURL: BASE_URL,
    timeout: 5000
});

//application/x-www-form-urlencoded;charset=UTF-8
service.interceptors.request.use(config => {

        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        //console.log('response',response)
        if (response.status === 200) {
            //console.log('response.data',response.data)
            return response.data;
        } else {
            Promise.reject().then(r => {
                console.log('请求配置有错啊', r);
            });
        }
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

export default service;
