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
                    :disabled="isSend"
                    @click="sendEmail(formRef)">
                  {{ sendButtonText }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="verifyCode">
            <el-input v-model="registerForm.verifyCode"/>
          </el-form-item>
          <el-form-item label="联系电话" prop="phoneNumber">
            <el-input
                v-model.number="registerForm.phoneNumber"
                type="text"
                autocomplete="off"/>
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
          <el-form-item label="店铺商标" prop="shopImage">
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
import {ElMessage} from "element-plus";
import {http} from '@/utils/http';
import {countDown} from '@/utils';
import router from '@/router/index'

export default {
  data() {
    return {
      registerForm: {
        email: '',
        verifyCode: '',
        shopName: '',
        desc: '',
        shopImage: '',
        phoneNumber: '',
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
          {required: true, message: '店铺商标不能为空', trigger: 'blur'}
        ],
        phoneNumber: [
          {required: true, message: '联系电话不能为空', trigger: 'blur'},
          {type: 'number', message: '请输入正确电话格式', trigger: ['blur', 'change']}

        ]
      },
      sendButtonText: '发送验证码',
      isSend: false,
    }
  },
  setup() {
    const formRef = ref<FormInstance>();
    return {
      formRef
    }
  },
  methods: {
    sendEmail: function (formR) {
      if(!formR)return;
      formR.validateField(['email'], (valid) => {
        if (valid) {
          this.isSend = true;
          const params = {
            url: 'account/send/email/verify',
            data: {
              toEmail: this.registerForm.email,
              fromEmail: "",
              context: ""
            },
            callBack: function (data) {
              ElMessage({
                message: '验证码发送成功',
                type: 'success'
              });
            }
          };
          http(params);
          countDown(60, (value) => {
            this.sendButtonText = value + 's后重试';
            if (value === 0) {
              this.sendButtonText = '发送验证码';
              this.isSend = false;
            }
          });
        }
      })

    },
    changeImage: function (uploadFile) {
      this.registerForm.shopImage = uploadFile.raw;
    },
    submit : function (formR){
      if (!formR) return;
      formR.validate((valid) => {
        if (valid) {
          const formData = new FormData();
          formData.append('photo', this.registerForm.shopImage);
          formData.append('userInfo', JSON.stringify({
            account: this.registerForm.email,
            nickname: this.registerForm.shopName,
            verifyCode: this.registerForm.verifyCode,
            password: "",
            image: "",
            roleType: 2,
            ext: this.registerForm.desc
          }));
          const params = {
            url: "/account/user/shop/register",
            isFile: true,
            data: formData,
            callBack: (res) => {
              ElMessage({
                message: '注册成功',
                type: 'success'
              });
              setTimeout(() => {
                router.push({
                  name: "login"
                });
              }, 1000);
            }
          }
          http(params);
        }
      });
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
  margin-top: -350px;
  text-align: center;
}

.login .login-box .top .logo {
  transform: scale(0.2);
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