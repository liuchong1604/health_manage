<template>
  <v-layout v-if="logined" column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>修改密码</v-toolbar-title>
      <v-spacer />
    </v-toolbar>
    <p></p>
    <v-card class="mx-auto" width="450">
      <v-card-title>修改密码</v-card-title>
      <v-divider />
      <p></p>
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="80px" class="demo-ruleForm">
        <el-col :span="18">
          <el-form-item label="旧密码" prop="oldPass">
            <el-input v-model="ruleForm.oldPass" type="password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input v-model="ruleForm.pass" type="password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input v-model="ruleForm.checkPass" type="password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="ruleForm.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <el-col :span="14">
              <el-input v-model="ruleForm.code" autocomplete="off"></el-input>
            </el-col>
            <el-col :span="10">
              <el-button @click="countDown('ruleForm')" :disabled="disabled" type="primary">{{ content }}</el-button>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-button @click="submit('ruleForm')" type="primary">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </v-card>
    <p></p>
  </v-layout>
</template>

<script>
import { getToken } from '../../api/auth'
import { modifyPwd } from '../../api/user'
import { sendSms } from '../../api/common'
export default {
  name: 'App',
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
    const validatePhone = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入手机号'));
      } setTimeout(() => {
        const phoneRules = /^1(3[0-9]|4[57]|5[0-35-9]|6[6]|8[0-9]|9[89]|7[013678])\d{8}$/;
        if (!phoneRules.test(value)) {
          callback(new Error("手机号格式不合法"))
        } else {
          callback();
        }
      }, 1000);
    };
    return {
      logined: false,
      disabled: false,
      content: "点击获取",
      totalTime: 60,
      ruleForm: {
        oldPass: "",
        pass: "",
        checkPass: "",
        phone: "",
        code: ""
      },
      rules: {
        oldPass: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        pass: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    if (getToken()) {
      this.logined = true;
    } else {
      this.logined = false;
      this.$message.error("你还未登录，请先登录");
      setTimeout(() => {
        this.$router.push("/login");
      }, 2000);
    }
  },
  methods: {
    countDown (formName) {
      const that = this;
      this.$refs[formName].validateField('phone', (error) => {
        if (error) {
          return
        };
        this.disabled = true;
        this.content = this.totalTime + "s后重试";
        const data = {
          phone: this.ruleForm.phone
        };
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
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields();
    },
    submit (formName) {
      if (getToken()) {
        this.logined = true;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const formData = {
              oldPass: this.ruleForm.oldPass,
              phone: this.ruleForm.phone,
              pass: this.ruleForm.pass,
              code: this.ruleForm.code
            }
            modifyPwd(this.$qs.stringify(formData))
              .then(resp => {
                console.log(resp.data.msg);
                if (resp.data.msg === "0") {
                  this.$message.error("验证码输入错误,请重新输入");
                  this.ruleForm.oldPass = "";
                } else if (resp.data.msg === "2") {
                  this.$message.error("旧密码输入错误,请重新输入");
                } else if (resp.data.msg === "1") {
                  this.$store
                    .dispatch("Logout")
                    .then(() => {
                      console.log("退出成功");
                    })
                    .catch(err => {
                      console.log(err);
                    });
                  this.$message({
                    dangerouslyUseHTMLString: true,
                    message: "修改成功,将前往登录页<a href='/login'>(立即前往?)</a>"
                  })
                  setTimeout(() => {
                    location.href = "/login";
                  }, 5000)
                }
              })
          }
        })
      } else {
        this.logined = false;
        this.$message.error("你还未登录，请先登录");
        setTimeout(() => {
          this.$router.push("/login");
        }, 2000);
      }
    }
  }
}
</script>

<style scoped>
</style>
