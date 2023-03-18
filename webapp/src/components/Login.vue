<template>
    <div class="container">
        <div class="card card-container">
            <form id="formLogin" class="form-signin">
                <input name="username" v-model="username" type="text" id="inputUsername" class="form-control"
                    placeholder="ユーザー名" required autofocus>
                <input name="password" v-model="password" type="password" id="inputPassword" class="form-control"
                    placeholder="パスワード" required>
                <button type="button" @click="send" class="btn btn-lg btn-primary btn-block btn-signin">ログイン</button>
            </form>
        </div>
    </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import axios from 'axios'

export default defineComponent({
    data() {
        return {
            username: '',
            password: '',
        }
    },
    methods: {
        send() {
            let body = new URLSearchParams()
            body.append('username', this.username);
            body.append('password', this.password);
            axios.post(
                '/authenticate',
                body)
                .then(function (response) {
                    console.log(JSON.stringify(response));
                    console.log(document.cookie);
                    window.location.href = "/";
                })
                .catch(function (error) {
                    console.log(JSON.stringify(error));
                    console.log(document.cookie);
                    // ログインが成功してもアクセス自体に対しては302で応答してくる。
                    // 応答メッセージ内のstatusをチェックする。
                    if (error.status == 200) {
                        window.location.href = "/";
                    } else {
                        window.location.href = "/login?error";
                    }
                });
        }
    }
});
</script>
<style scoped>
/*
 * Specific styles of signin component
 */
/*
 * General styles
 */
body,
html {
    height: 100%;
    background-repeat: no-repeat;
    background-image: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));
}

.card-container.card {
    max-width: 350px;
    padding: 40px 40px;
}

.btn {
    font-weight: 700;
    height: 36px;
    -moz-user-select: none;
    -webkit-user-select: none;
    user-select: none;
    cursor: default;
}

/*
 * Card component
 */
.card {
    background-color: #F7F7F7;
    /* just in case there no content*/
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    /* shadows and rounded borders */
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

/*
 * Form styles
 */

.form-signin #inputUsername,
.form-signin #inputPassword {
    direction: ltr;
    height: 44px;
    font-size: 16px;
}

.form-signin input[type=email],
.form-signin input[type=password],
.form-signin input[type=text],
.form-signin button {
    width: 100%;
    display: block;
    margin-bottom: 10px;
    z-index: 1;
    position: relative;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin .form-control:focus {
    border-color: rgb(104, 145, 162);
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgb(104, 145, 162);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgb(104, 145, 162);
}

.btn.btn-signin {
    /*background-color: #4d90fe; */
    background-color: rgb(104, 145, 162);
    /* background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/
    padding: 0px;
    font-weight: 700;
    font-size: 14px;
    height: 36px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    border: none;
    -o-transition: all 0.218s;
    -moz-transition: all 0.218s;
    -webkit-transition: all 0.218s;
    transition: all 0.218s;
}

.btn.btn-signin:hover,
.btn.btn-signin:active,
.btn.btn-signin:focus {
    background-color: rgb(12, 97, 33);
}
</style>