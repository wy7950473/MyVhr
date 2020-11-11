import axios from 'axios';
import { Message } from 'element-ui';

axios.interceptors.response.use(response => {
    if (response.status && response,status == 200 && response.data.status == 500) {
        Message.error({
            message:response.data.msg
        });
        return false;
    }
    if (response.data.msg) {
        Message.success({message:response.data.msg});
    }
    return response.data;
},error => {
    if(error.response.status == 504 || error.response.status == 404) {
        Message.error({
            message:"服务器没了"
        });
    } else if (error.response.status == 403) {
        Message.error({
            message:"权限不足，请联系管理员"
        });
    } else if (error.response.status == 401) {
        Message.error({
            message:"尚未登录，请登录"
        });
    } else {
        if (error.response.data.msg){
            Message.error({
                message:error.response.data.msg
            });
        } else {
            Message.error({
                message:"未知错误"
            });
        }
        return false;
    }
});

let base = '';

export const postKeyValueRequest = ((url,data)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data,
        transformRequest:[function(params){
            let ret = '';
            for(let i in params){
                ret += encodeURIComponent(i)+'='+encodeURIComponent(params[i])+'&';
            }
            return ret;
        }],
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        }
    });
});

export const postRequest = (url,data) => {
    return axios({
        method:'post',
        url:`${base}${url}`,
        data
    })
}

export const putRequest = (url,data) => {
    return axios({
        method:'put',
        url:`${base}${url}`,
        data
    })
}

export const getRequest = (url,data) => {
    return axios({
        method:'get',
        url:`${base}${url}`,
        data
    })
}

export const deleteRequest = (url,data) => {
    return axios({
        method:'delete',
        url:`${base}${url}`,
        data
    })
}



