<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>用户注册</v-toolbar-title>
      <v-spacer dark />
      <v-breadcrumbs :items="items"> </v-breadcrumbs>
    </v-toolbar>
    <v-col>
      <p></p>
    </v-col>
    <v-card class="mx-auto" width="400" height="500">
      <v-card-title>用户注册</v-card-title>
      <v-divider />
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-row justify="center">
          <v-col cols="12"></v-col>
          <v-col cols="11">
            <v-text-field v-model="name" :counter="11" :rules="nameRules" color="deep-purple" prepend-icon="mdi-account" filled label="请输入你的登录昵称" single-line dense required></v-text-field>
            <v-text-field v-model="phone" :rules="[phoneRules]" color="deep-purple" prepend-icon="mdi-phone" filled label="请输入你的手机号" single-line dense required>
            </v-text-field>
            <v-row no-gutters>
              <v-col md="8">
                <v-text-field v-model="code" :rules="codeRules" dense prepend-icon="mdi-amazon-alexa" filled label="请输入短信验证码" single-line></v-text-field>
                <v-snackbar v-model="snackbar" :timeout="timeout" color="error" top>
                  {{ text }}
                  <v-btn @click="close" text>
                    确定
                  </v-btn>
                </v-snackbar>
              </v-col>
              <v-col md="4">
                <v-btn @click="countDown" :disabled="disabled" block height="41" color="primary">
                  {{ content }}
                </v-btn>
              </v-col>
            </v-row>
            <v-text-field v-model="password" :rules="passwordRules" :type="show ? 'text' : 'password'" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" @click:append="show = !show" color="deep-purple" prepend-icon="mdi-lock" filled label="请输入密码" single-line dense required>
            </v-text-field>
            <v-row>
              <v-col cols="12" md="1"></v-col>
              <v-col md="7" cols="12">
                <v-checkbox v-model="agreement" :rules="agreementRules" color="deep-purple">
                  <template v-slot:label>
                    I agree the&nbsp;《
                    <a @click.stop.prevent="dialog = true" href="#">
                      Terms
                    </a>
                    》.
                  </template>
                </v-checkbox>
              </v-col>
              <v-col cols="12" md="2">
                <v-btn @click="register" color="primary">
                  快速注册
                </v-btn>
              </v-col>
              <v-dialog v-model="dialog" absolute max-width="400" persistent>
                <v-card>
                  <v-card-title class="headline grey lighten-3">
                    Legal
                  </v-card-title>
                  <v-card-text>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit
                    esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
                    occaecat cupidatat non proident, sunt in culpa qui officia
                    deserunt mollit anim id est laborum.
                  </v-card-text>
                  <v-divider></v-divider>
                  <v-card-actions>
                    <v-btn @click="(agreement = false), (dialog = false)" text>
                      No
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn @click="(agreement = true), (dialog = false)" class="white--text" color="deep-purple accent-4">
                      Yes
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-row>
            已有账号,去
            <nuxt-link to="/login">
              登录
            </nuxt-link>
            .
          </v-col>
        </v-row>
      </v-form>
    </v-card>
    <v-col cols="12"></v-col>
  </v-layout>
</template>

<script>
import { sendSms, register } from "../api/user";

export default {
  data: () => ({
    items: [
      {
        text: "系统首页",
        disabled: false,
        href: "/"
      },
      {
        text: "注册",
        disabled: true
      }
    ],
    valid: true,
    name: "",
    nameRules: [v => !!v || "品牌名称不能为空"],
    phone: "",
    password: "",
    passwordRules: [v => !!v || "品牌名称不能为空"],
    code: "",
    codeRules: [v => !!v || "验证码不能为空"],
    agreement: false,
    agreementRules: [v => !!v || "This field is required"],
    dialog: false,
    disabled: false,
    content: "点击获取",
    totalTime: 60,
    snackbar: false,
    text: "",
    timeout: 8000,
    show: false
    // rules: {
    //   length: len => v =>
    //     (v || "").length >= len || `Invalid character length, required ${len}`,
    //   name: v =>
    //     (v && v.length <= 10) || "Name must be less than 10 characters",
    //   phone: v =>
    //     v.match(
    //       /^1(3[0-9]|4[57]|5[0-35-9]|6[6]|8[0-9]|9[89]|7[03678])\d{8}$/
    //     ) || "手机号不合法",
    //   password: v =>
    //     (v || "").match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/) ||
    //     "Password must contain an upper case letter, a numeric character, and a special character"
    // }
  }),
  methods: {
    countDown () {
      const that = this;
      if (this.phone === "") {
        this.snackbar = true;
        this.text = "请输入手机号";
      } else if (this.phoneRules(this.phone) === "手机号格式错误") {
        this.snackbar = true;
        this.text = "手机号格式有误,请重新输入";
      } else {
        this.disabled = true;
        const data = {
          phone: this.phone
        };
        sendSms(this.$qs.stringify(data)).then(response => {
          console.log(response);
          if (response.status === 201) {
            this.snackbar = true;
            this.text = "发送成功";
          } else {
            this.snackbar = true;
            this.text = "发送失败,请重新获取";
            window.clearInterval(this.cloak);
            this.content = "重新获取";
            this.totalTime = 60;
            this.disabled = false; // 这里重新开启
          }
        });
        this.content = this.totalTime + "后重试";
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
    close () {
      this.snackbar = false;
      this.text = "";
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
    register () {
      if (this.$refs.form.validate()) {
        const formData = {
          username: this.name,
          phone: this.phone,
          code: this.code,
          password: this.password
        };
        register(this.$qs.stringify(formData)).then(response => {
          if (response.status === 201) {
            this.snackbar = true;
            this.text = "注册成功,将前往<a href='/login'>登录页(立即前往?)</a>";
            setTimeout(() => {
              location.href = "/login";
            }, 5000);
          }
        });
      }
    }
  }
};
</script>

<style scoped></style>
