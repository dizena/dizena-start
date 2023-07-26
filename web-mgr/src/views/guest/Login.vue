<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">登录页面</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">

        <el-form-item prop="username">
          <el-input v-model="param.username" placeholder="账户">
            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input type="password" placeholder="密码" v-model="param.password">
            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
          </el-input>
        </el-form-item>

        <!--
        <el-form-item prop="code">
          <el-input type="text" placeholder="验证码" v-model="param.code" style="width: 140px;" >
            <el-button slot="prepend" icon="el-icon-lx-question"></el-button>
          </el-input>
          <img :src="image" alt="验证码" class="math-img" @click="getImage">
        </el-form-item>
        -->

        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>

      </el-form>
    </div>
  </div>
</template>

<script>

import {AuthLogin,GetConfig} from '../../utils/Api';
//todo 图片验证码接口介接入

export default {
  data: function () {
    return {
      param: {
        username: '',
        password: '',
        code: '',
        uuid: ''
      },
      rules: {
        username: [{required: true, message: '请输入账户', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        code: [{required: true, message: '请输入验证码', trigger: 'blur'}],
      },
      image: '',
    };
  },
  methods: {
    submitForm() {
      this.$refs.login.validate(valid => {
        let formData = new FormData();
        formData.append("account", this.param.username);
        formData.append("password", this.param.password);
        formData.append("code", this.param.code);
        formData.append("uuid", this.param.uuid);

        AuthLogin(formData).then((res) => {
          if (res.code === 200) {
            this.$message.success('登录成功');
            sessionStorage.setItem('current_user', JSON.stringify(res.content));
            sessionStorage.setItem('AccessToken', res.content.token);
            sessionStorage.setItem('role', res.content.roles);
            this.$router.push('/');
          } else {
            this.$message.error(res.msg);
            this.getImage();
            return false;
          }
        }).catch((err) => {
          console.log(err)
        })

      });
    },
    getConfig() {
      GetConfig('mgr-role').then(res => {
        if (res.code == 200) {
          //sessionStorage.setItem('mgrRole', res.content);
          //todo 在配置里添加
          sessionStorage.setItem('mgrRole', "master,admin");
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  },
  created() {
    this.getConfig();
  }
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../../assets/img/login-bg.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: orangered;
}

.math-img {
  width: 130px;
  height: 30px;
  margin-left: 15px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
