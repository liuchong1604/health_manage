<template>
  <v-row align="center" justify="center">
    <v-card height="350" width="550">
      <v-toolbar
        dark
        src="https://cdn.vuetifyjs.com/images/backgrounds/vbanner.jpg"
      >
        <v-flex xs4></v-flex>
        <v-toolbar-title>智检平台后台登录</v-toolbar-title>
      </v-toolbar>
      <v-card-text>
        <v-form ref="loginform" :lazy-validation="lazy">
          <v-flex xs11>
            <v-text-field
              v-model="username"
              name="username"
              prepend-icon="mdi-account-circle"
              label="请输入用户名"
              :rules="nameRules"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs11>
            <v-text-field
              v-model="password"
              name="username"
              prepend-icon="mdi-lock"
              :counter="5"
              label="请输入密码"
              :type="show ? 'text' : 'password'"
              :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
              :rules="passwordRules"
              @click:append="show = !show"
              autocomplete="false"
              required
            ></v-text-field>
          </v-flex>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-col class="text-center" cols="12" sm="4">
          <v-btn
            color="pink"
            :loading="valid"
            :disabled="valid"
            @click="handleLogin"
            >登录</v-btn
          >
        </v-col>
        <v-col class="text-center" cols="12" sm="4"> </v-col>
        <v-col class="text-center" cols="12" sm="4">
          <v-btn color="pink" @click="reset">重置</v-btn>
        </v-col>
      </v-card-actions>
    </v-card>
  </v-row>
</template>

<script>
export default {
  name: "Login",
  data: () => ({
    valid: false,
    username: "",
    nameRules: [v => !!v || "Name is  required"],
    password: "",
    passwordRules: [
      v => !!v || "password is required",
      v => (v && v.length >= 6) || "password must be more than 6 characters"
    ],
    lazy: false,
    show: false,
    loader: null
  }),
  watch: {
    loader() {
      const l = this.loader;
      this[l] = !this[l];
      setTimeout(() => {
        this[l] = false;
      }, 3000);
      this.loader = null;
    }
  },
  methods: {
    handleLogin() {
      if (this.$refs.loginform.validate()) {
        const formData = {
          username: this.username,
          password: this.password
        };
        //console.log(this.$qs.stringify(formData));
        this.$store
          .dispatch("Login", this.$qs.stringify(formData))
          .then(() => {
            this.loader = "valid";
            this.$router.push({ path: "/" });
          })
          .catch(() => {
            this.loader = "valid";
          });
      }
    },
    // doLogin() {
    //   if (this.$refs.form.validate()) {

    //     this.loader = "valid";
    //   }
    // },
    reset() {
      this.$refs.loginform.reset();
    }
  }
};
</script>

<style scoped></style>
