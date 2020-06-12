<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>重置密码</v-toolbar-title>
      <v-spacer />
    </v-toolbar>
    <p></p>
    <v-card v-if="isVerify" class="mx-auto" width="700">
      <v-card-title>身份验证</v-card-title>
      <v-divider />
      <v-card-subtitle>
        <v-icon>mdi-alert</v-icon>
        如果您忘记了密码，您可以使用您的账户名创建一个密码。点击获取验证码后我们会给您注册时候所使用的手机 发送一个验证码。您正确填写验证码后将会切换到创建新密码页面，您可以重新设置密码。
      </v-card-subtitle>
      <v-form ref="verifyForm" v-model="valid" lazy-validation>
        <v-row>
          <v-col md="2"></v-col>
          <v-col md="7">
            <v-text-field v-model="phone" :rules="[phoneRules]" color="deep-purple" prepend-icon="mdi-phone" filled label="请输入你的手机号" single-line dense required>
            </v-text-field>
            <v-row no-gutters>
              <v-col md="8">
                <v-text-field v-model="code" :rules="codeRules" dense prepend-icon="mdi-amazon-alexa" filled label="请输入短信验证码" single-line></v-text-field>
              </v-col>
              <v-col md="4">
                <v-btn @click="countDown" :disabled="disabled" block height="41" color="primary">
                  {{ content }}
                </v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col md="4"></v-col>
              <v-col md="8">
                <v-btn @click="next" color="primary">下一步</v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-form>
    </v-card>
    <v-card v-if="!isVerify" class="mx-auto" width="700">
      <v-card-title>创建新密码</v-card-title>
      <v-divider />
      <v-row>
        <v-col md="2"></v-col>
        <v-col md="7">
          <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="80px" class="demo-ruleForm">
            <el-form-item label="密码" prop="pass">
              <el-input v-model="ruleForm.pass" type="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input v-model="ruleForm.checkPass" type="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="submit('ruleForm')" type="primary">提交</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </v-col>
      </v-row>
    </v-card>
    <p></p>
  </v-layout>
</template>

<script>
import { sendSms } from '../../api/common'
import { verify, forgetPwd } from '../../api/user'
export default {
  data () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      isVerify: true,
      valid: true,
      phone: "",
      code: "",
      codeRules: [v => !!v || "验证码不能为空"],
      disabled: false,
      content: "点击获取",
      totalTime: 60,
      ruleForm: {
        pass: '',
        checkPass: ''
      },
      rules: {
        pass: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    countDown () {
      const that = this;
      if (this.phone === "") {
        this.$message.error("请输入手机号");
      } else if (this.phoneRules(this.phone) === "手机号格式错误") {
        this.$message.error("手机号格式有误,请重新输入");
      } else {
        this.disabled = true;
        const data = {
          phone: this.phone
        };
        console.log(data);
        sendSms(this.$qs.stringify(data)).then(response => {
          console.log(response);
          if (response.status === 201) {
            this.$message.success("发送成功");
          } else {
            this.$message.error("发送失败，请重新获取")
            window.clearInterval(this.cloak);
            this.content = "重新获取";
            this.totalTime = 60;
            this.disabled = false; // 这里重新开启
          }
        });
        that.content = this.totalTime + "后重试";
        that.cloak = setInterval(function () {
          // 定时器里面的this不是指向form组件
          that.totalTime--;
          if (that.totalTime > 0) {
            that.content = that.totalTime + "s后重试";
          }
          if (that.totalTime <= 0) {
            window.clearInterval(that.cloak); // 当倒计时小于等于0时清除定时器
            that.content = "重新获取";
            that.totalTime = 60;
            that.disabled = false; // 这里重新开启
          }
        }, 1000);
      }
    },
    phoneRules (v) {
      if (v.length === 0) {
        return "手机号不能为空";
      }
      if (
        !/^1(3[0-9]|4[57]|5[0-35-9]|6[6]|8[0-9]|9[89]|7[013678])\d{8}$/.test(v)
      ) {
        return "手机号格式错误";
      }
      return true;
    },
    next () {
      if (this.$refs.verifyForm.validate()) {
        verify(this.phone, this.code)
          .then(resp => {
            if (resp.status !== 201) {
              this.$message.error("验证码错误");
            } else {
              this.isVerify = false;
            }
          })
          .catch(err => {
            console.log("err: " + err);
          })
      }
    },
    resetForm (formName) {
      this.$refs[formName].resetFields();
    },
    submit (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const formData = {
            phone: this.phone,
            password: this.ruleForm.pass
          }
          forgetPwd(this.$qs.stringify(formData))
            .then(resp => {
              if (resp.status !== 200) {
                this.$message.error("修改失败,请重新尝试!")
              } else {
                this.$message({
                  dangerouslyUseHTMLString: true,
                  message: "修改成功,将前往<a href='/login'>登录页(立即前往?)</a>"
                })
                setTimeout(() => {
                  location.href = "/login";
                }, 5000)
              }
            })
            .catch(err => {
              console.log("err： " + err);
            })
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
