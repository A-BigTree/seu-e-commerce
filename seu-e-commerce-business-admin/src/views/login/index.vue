<template>
  <div class="login">
    <div class="login-box">
      <div class="top">
        <div class="logo">
          <img src="@/assets/img/logo.png" alt="">
        </div>
      </div>
      <div class="mid">
        <el-form :model="dataForm"
                 :rules="dataRule"
                 ref="formRef"
                 @keyup.enter.native="dataFormSubmit(formRef)"
                 status-icon>
          <el-form-item prop="userName">
            <el-input class="info"
                      v-model="dataForm.userName"
                      placeholder="帐号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input class="info"
                      v-model="dataForm.password"
                      type="password"
                      placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item prop="type">
            <el-radio-group v-model="dataForm.roleType">
              <el-radio :label="2">商家管理员</el-radio>
              <el-radio :label="3">平台管理员</el-radio>
            </el-radio-group>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-link type="primary" href="/register">
              现在去开店？
            </el-link>
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                size="large"
                @click="dataFormSubmit(formRef)">
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import type {FormInstance} from "element-plus";
import {ref} from 'vue';
import {md5} from "js-md5";
import {MD5_SALT} from '@/utils/config';
import {setToken, getToken} from '@/utils/cookies';
import {http} from '@/utils/http'
import {ElMessage} from "element-plus";

export default {
  data() {
    return {
      dataForm: {
        userName: '',
        password: '',
        roleType: 2,
      },
      dataRule: {
        userName: [
          {required: true, message: '帐号不能为空', trigger: 'blur'},
          {type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change']}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ]
      },
    }
  },
  setup(){
    const formRef = ref<FormInstance>();
    return {
      formRef
    }
  },
  methods: {
    dataFormSubmit(ref) {
      if (!ref) return;
      ref.validate((valid) => {
        if (valid) {
          const params = {
            url: "/account/user/login",
            data: {
              account: this.dataForm.userName,
              password: md5(this.dataForm.password + MD5_SALT),
              roleType: this.dataForm.roleType
            },
            callBack: (res) => {
              const session = res.data;
              console.log(session);
              setToken(session.token);
              ElMessage({
                message: "登录成功",
                type: "success"
              });
              setTimeout(() => {
                this.$router.back();
              }, 1000);
            },
            errCallBack: (res) => {
              if (res.code === 503) {
                ElMessage({
                  message: "账号或者密码不正确",
                  type: "error"
                });
              } else {
                ElMessage({
                  message: res.msg || '服务器出了点小差~',
                  type: 'error'
                });
              }
            }
          };
          http(params)
        }
      })

    },

  }
}
</script>

<style lang="scss">
.login {
  width: 100%;
  height: 100%;
  background: url(@/assets/img/login-bg.png) no-repeat;
  background-size: cover;
  position: fixed;
}

.login .login-box {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  height: 100%;
  padding-top: 10%;
}

.login .login-box .top {
  margin-bottom: -180px;
  margin-top: -250px;
  text-align: center;
}

.login .login-box .top .logo {
  transform: scale(0.3);
  font-size: 0;
  max-width: 50%;
  margin: 0;
}

.login .login-box .top .company {
  font-size: 16px;
  margin-top: 10px;
}

.login .login-box .mid {
  font-size: 14px;
}

.login .login-box .mid .item-btn {
  margin-left: auto;
}

.login .login-box .mid .item-btn input {
  border: 0;
  width: 100%;
  height: 40px;
  /*box-shadow: 0;*/
  background: #1f87e8;
  color: #fff;
  border-radius: 3px;
}

.info {
  width: 410px;
}

.login-captcha {
  height: 40px;
}

.login .login-box .bottom {
  position: absolute;
  bottom: 10%;
  width: 100%;
  color: #999;
  font-size: 12px;
  text-align: center;
}
</style>