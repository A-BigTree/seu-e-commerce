<template>
  <div class="login">
    <div class="login-box">
      <div class="top">
        <div class="logo">
          <img src="@/assets/img/logo.png"
               alt="">
        </div>
      </div>
      <div class="mid">
        <el-form
            ref="formRef"
            label-width="120px"
            :model="registerForm"
            :rules="formRules"
            status-icon>
          <el-form-item label="店铺名称" prop="shopName">
            <el-input v-model="registerForm.shopName"/>
          </el-form-item>
          <el-form-item label="注册邮箱" prop="email">
            <el-input v-model="registerForm.email">
              <template #append>
                <el-button
                    type="primary"
                    @click="sendEmail(formRef)">
                  {{ sendButtonText }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="verifyCode">
            <el-input v-model="registerForm.verifyCode"/>
          </el-form-item>
          <el-form-item label="店铺介绍" prop="desc">
            <el-input
                v-model="registerForm.desc"
                placeholder="请输入店铺介绍"
                :autosize="{ minRows: 4, maxRows: 4 }"
                maxlength="100"
                show-word-limit
                type="textarea"/>
          </el-form-item>
          <el-form-item label="营业许可证" prop="shopImage">
            <el-upload
                v-model="registerForm.shopImage"
                ref="uploadRef"
                :on-change="changeImage"
                action="#"
                :limit="1"
                list-type="picture-card"
                :auto-upload="false">
              <el-icon>
                <Plus/>
              </el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                size="large"
                @click="submit(formRef)">
              提交审核
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>

</template>

<script lang="ts">
import type {FormInstance} from "element-plus";
import {ref} from "vue";

export default {
  data() {
    return {
      registerForm: {
        email: '',
        verifyCode: '',
        shopName: '',
        desc: '',
        shopImage: '',
      },
      formRules: {
        shopName: [
          {required: true, message: '店铺名称不能为空', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '注册邮箱不能为空', trigger: 'blur'},
          {type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change']}
        ],
        verifyCode: [
          {required: true, message: '验证码不能为空', trigger: 'blur'}
        ],
        desc: [
          {required: true, message: '店铺介绍不能为空', trigger: 'blur'}
        ],
        shopImage: [
          {required: true, message: '营业许可证不能为空', trigger: 'blur'}
        ]
      },
      sendButtonText: '发送验证码',
    }
  },
  setup() {
    console.log("SetUp");
    const formRef = ref<FormInstance>();
    return {
      formRef
    }
  },
  methods: {
    sendEmail: function (formR) {
      console.log("Click Send email");
      if(!formR)return;
      formR.validateField(['email'], (valid) => {
        if (valid) {
          // TODO 发送验证码
        }
      })

    },
    changeImage: function (uploadFile) {
      this.registerForm.shopImage = uploadFile.raw;
      console.log(this.shopImage);
    },
    submit : function (formR){
      if (!formR) {
        console.log('error');
        return;
      }
      formR.validate((valid) => {
        if (valid) {
          console.log('submit');
          // TODO 注册逻辑
        }
      })
    }
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
</style>