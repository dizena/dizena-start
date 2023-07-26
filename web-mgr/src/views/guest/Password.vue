<template>
  <div>
    <div style="width: 40%;margin-left: auto;margin-right: auto;">
      <div style="margin: 20px;"></div>
      <el-form label-position="right" label-width="80px" :model="form">
        <el-form-item label="原密码">
          <el-input v-model="form.old" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="form.p1" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认下">
          <el-input v-model="form.p2" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
import {LoginUserUpdatePassword} from '@/utils/Api';

export default {
  name: "password",
  data() {
    return {
      form: {
        old: '',
        p1: '',
        p2: ''
      }
    };
  },
  methods: {
    submitForm() {
      if (this.form.old != '' && this.form.p1 != null && this.form.p2 == this.form.p1){
        LoginUserUpdatePassword(this.form).then(res=>{
          if(res.code == 200 && res.content == "ok"){
            this.$message.success("修改密码成功！退出重新登录")
            sessionStorage.removeItem('current_user');
            sessionStorage.removeItem('AccessToken');
            sessionStorage.removeItem('role');
            this.$router.push('/Login');
          }else{
            this.$message.error(res.content)
          }
        })
      }else{
        this.$message.error("输入为空不一致错误")
      }
    },
    resetForm() {
      this.form = {
        old: '',
        p1: '',
        p2: ''
      }
    }

  }
}
</script>

<style scoped>

</style>