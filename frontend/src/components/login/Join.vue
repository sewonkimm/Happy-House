<template>
  <div class="join">
    <v-card ref="form" outlined class="card">
      <v-card-text>
        <v-text-field
          v-model="userEamil"
          class="input"
          label="Email"
          :rules="[rules.required, rules.email]"
          color="#203eac"
          clearable
        ></v-text-field>

        <v-text-field
          v-model="userName"
          class="input"
          label="이름"
          :rules="[rules.required]"
          color="#203eac"
          clearable
        ></v-text-field>

        <v-text-field
          v-model="userPassword"
          class="input"
          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
          :rules="[rules.required, rules.min]"
          :type="show1 ? 'text' : 'password'"
          name="input-10-1"
          label="비밀번호"
          color="#203eac"
          counter
          @click:append="show1 = !show1"
        ></v-text-field>
        <v-text-field
          v-model="userPassword2"
          class="input"
          :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
          :rules="[rules.required, rules.checkPassword]"
          :type="show2 ? 'text' : 'password'"
          name="input-10-1"
          label="비밀번호 확인"
          color="#203eac"
          counter
          @click:append="show2 = !show2"
        ></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-btn
          elevation="2"
          large
          rounded
          dense
          width="300"
          height="60"
          color="#fedccc"
          class="loginButton"
          @click="join"
        >회원가입</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: "Join",
  data() {
    return {
      userEamil: "",
      userName: "",
      userPassword: "",
      show1: false,
      show2: false,
      rules: {
        required: value => !!value || "내용을 입력해주세요.",
        email: value => {
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
          return pattern.test(value) || "이메일을 입력해주세요.";
        },
        min: value => value.length >= 8 || "8자 이상 입력해주세요.",
        checkPassword: value => {
          return value === this.userPassword || "비밀번호가 틀립니다!";
        }
      }
    };
  },
  methods: {
    join: function() {
      const params = {
        userEmail: this.userEamil,
        userName: this.userName,
        userPassword: this.userPassword,
        userType: 0
      };

      axios
        .post(SERVER_URL + "/register", params)
        .then(response => {
          console.log(response.data);
          this.$router.push("/");
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped src="@/assets/css/login/Join.css" />
