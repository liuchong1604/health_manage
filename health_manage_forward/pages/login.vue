<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>用户登录</v-toolbar-title>
      <v-spacer />
      <v-breadcrumbs :items="items"> </v-breadcrumbs>
    </v-toolbar>
    <v-col cols="12">
      <p></p>
    </v-col>
    <v-card class="mx-auto" width="400" height="450">
      <v-card-title>用户登录</v-card-title>
      <v-divider />
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-snackbar v-model="snackbar" :timeout="timeout" color="error" top>
          {{ text }}
          <v-btn @click="close" text>
            确定
          </v-btn>
        </v-snackbar>
        <v-row justify="center">
          <v-col cols="12"> </v-col>
          <v-col cols="11">
            <v-text-field v-model="name" :counter="11" :rules="nameRules" color="deep-purple" prepend-icon="mdi-account" filled label="请输入登录昵称" single-line dense required></v-text-field>
            <v-text-field v-model="password" :counter="5" :type="show ? 'text' : 'password'" :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'" :rules="passwordRules" @click:append="show = !show" filled color="deep-purple" label="请输入密码" autocomplete="false" prepend-icon="mdi-lock" single-line dense required></v-text-field>
            <v-row no-gutters>
              <v-col md="8" cols="2">
                <v-text-field v-model="code" :rules="codeRules" prepend-icon="mdi-pencil" dense filled label="请输入验证码" single-line>
                </v-text-field>
              </v-col>
              <v-col @click="refreshCode" md="4" cols="2">
                <!-- <v-input>验证码</v-input> -->
                <!-- <v-btn height="41" color="primary">点击获取</v-btn> -->
                <!-- <img :src="imagePath" width="98" height="40" /> -->
                <s-identify :identifyCode="identifyCode"></s-identify>
                <span class="body-2">点击图片刷新</span>
              </v-col>
            </v-row>
            <v-row no-gutters>
              <v-col md="1"></v-col>
              <v-col md="7">
                <v-checkbox v-model="agreement" label="记住我?"> </v-checkbox>
              </v-col>
              <v-col md="1"></v-col>
              <v-col md="3">
                <v-btn @click="handleLogin" color="success">
                  登录
                </v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col md="2">
              </v-col>
              <v-spacer></v-spacer>
              <v-col md="5">
                <nuxt-link to="/register">立即注册</nuxt-link>
              </v-col>
              <v-spacer></v-spacer>
              <v-col md="5">
                <nuxt-link to="/forgetpw">忘记密码?</nuxt-link>
              </v-col>
              <v-spacer></v-spacer>
            </v-row>
          </v-col>
        </v-row>
      </v-form>
    </v-card>
    <v-col></v-col>
  </v-layout>
</template>

<script>
import SIdentify from "../components/identify.vue";
export default {
  components: {
    SIdentify
  },
  data: () => ({
    items: [
      {
        text: "系统首页",
        disabled: false,
        href: "/"
      },
      {
        text: "登录",
        disabled: true
      }
    ],
    valid: true,
    name: "",
    nameRules: [
      v => !!v || "This field is required",
      v => (v && v.length <= 10) || "Name must be less than 10 characters"
    ],
    password: "",
    passwordRules: [
      v => !!v || "This field is required",
      v => v.length >= 6 || "password must be more than 6 characters"
    ],
    show: false,
    code: "",
    codeRules: [v => !!v || "This field is required"],
    agreement: false,
    identifyCodes:
      "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
    identifyCode: "",
    oldidentifyCode: "",
    snackbar: false,
    text: "",
    timeout: 8000,
    expire: 0
    // errMsg: "验证码错误"
    // rules: {
    //   length: len => v =>
    //     (v || "").length >= len || `Invalid character length, required ${len}`,
    //   password: v =>
    //     (v || "").match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/) ||
    //     "Password must contain an upper case letter, a numeric character, and a special character",
    //   required: v => !!v || "This field is required",
    //   name: v => (v && v.length <= 10) || "Name must be less than 10 characters"
    // }
  }),

  mounted () {
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    handleLogin () {
      this.refreshCode();
      if (this.$refs.form.validate()) {
        if (this.code.toLowerCase() !== this.oldidentifyCode.toLowerCase()) {
          this.snackbar = true;
          this.text = "验证码错误，请重新输入";
        } else {
          if (this.agreement) {
            this.expire = 1;
          }
          const formData = {
            username: this.name,
            password: this.password,
            expire: this.expire
          };
          this.$store
            .dispatch("Login", this.$qs.stringify(formData))
            .then(resp => {
              console.log(resp.data.msg);
              if (resp.data.msg) {
                this.$message.error("用户名或者密码错误");
              } else {
                this.$message.success("登录成功,即将前往首页");
                setTimeout(() => {
                  location.href = "/";
                }, 2000);
              }
            })
            .catch(err => {
              console.log(err);
            });
        }
      }
    },
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode () {
      this.oldidentifyCode = this.identifyCode;
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode (o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
          this.randomNum(0, this.identifyCodes.length)
        ];
      }
    },
    close () {
      this.snackbar = false;
      this.text = "";
    }
  }
};
</script>

<style scoped></style>
