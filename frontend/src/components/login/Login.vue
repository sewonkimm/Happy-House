<template>
  <div class="login">
    <v-card ref="form" outlined class="card">
      <v-card-text>
        <v-text-field
          v-model="user.userEmail"
          class="input"
          label="Email"
          :rules="[rules.required, rules.email]"
          color="#203eac"
          clearable
        ></v-text-field>

        <v-text-field
          v-model="user.userPassword"
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
      </v-card-text>
      <v-card-actions>
        <div class="buttonContainer">
          <v-btn
            elevation="2"
            large
            rounded
            dense
            width="300"
            height="60"
            color="#fedccc"
            class="loginButton"
            @click="login"
            >Login</v-btn
          >
          <v-btn
            elevation="2"
            large
            rounded
            dense
            width="300"
            height="60"
            color="#fedccc"
            class="loginButton"
            @click="goJoin"
            >회원 가입</v-btn
          >
        </div>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      dialog: false,
      user : {
        userEmail: "",
        userPassword: ""
      },
      show1: false,
      rules: {
        required: (value) => !!value || '내용을 입력해주세요.',
        email: (value) => {
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
          return pattern.test(value) || '이메일을 입력해주세요.';
        },
        min: (value) => value.length >= 8 || '8자 이상 입력해주세요.',
      },
    };
  },
  computed: {
    nextRoute() {
      return this.$route.params.nextRoute ? this.$route.params.nextRoute : "";
    }
  },
  methods: {
    login: function() {
      if(this.user.userEmail !== "" || this.user.userPassword !== ""){
        this.$store
          .dispatch("LOGIN", this.user)
          .then(response => {
            if(this.$store.getters.getAccessToken === undefined){
              alert("아이디, 비밀번호가 올바르지 않습니다.");
            }
            else {
              this.$router.replace(`/${this.nextRoute}`)
            }
          })
          .catch(error => {
            console.log(error);
        });
      }
    },
    goJoin: function() {
      this.$emit("open-join-page");
    }
  }
};
</script>

<style scoped src="@/assets/css/login/Login.css" />
