<template>
  <div id="app">
    <b-navbar toggleable="lg" type="dark" variant="success">
      <b-navbar-brand href="#">TELPTracker</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <template>
            <b-icon-house-door variant="light" font-scale="1.5" shift-v="-5"  @click="$router.push('/home')"></b-icon-house-door>
          </template>

          <b-nav-item  @click="$router.push('/home')">На главную</b-nav-item>

          <b-navbar-nav v-if="currentUser && !showAdminBoard" class="ml-auto">
            <template>
              <b-icon-list-task variant="light" font-scale="1.7" shift-v="-5"  @click="$router.push('/tasks')"></b-icon-list-task>
            </template>
            <b-nav-item  @click="$router.push('/tasks')">Мои задачи</b-nav-item>
          </b-navbar-nav>

          <b-navbar-nav v-if="showAdminBoard" class="ml-auto">
            <template>
              <b-icon-list-task variant="light" font-scale="1.7" shift-v="-5"  @click="$router.push('/tasks')"></b-icon-list-task>
            </template>
            <b-nav-item  @click="$router.push('/tasks')">Задачи</b-nav-item>
            <template>
              <b-icon-table variant="light" font-scale="1.35" shift-v="-6"  @click="$router.push('/tablet')"></b-icon-table>
            </template>
            <b-nav-item  @click="$router.push('/tablet')">Планшет</b-nav-item>
          </b-navbar-nav>

        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav v-if="!currentUser" class="ml-auto">
          <template>
            <b-icon-people variant="light" font-scale="1.5" shift-v="-5"  @click="$router.push('/register')"></b-icon-people>
          </template>
          <b-nav-item  @click="$router.push('/register')">Регистрация</b-nav-item>

          <template>
            <b-icon-box-arrow-in-right variant="light" font-scale="1.8" shift-v="-3"  @click="$router.push('/login')"></b-icon-box-arrow-in-right>
          </template>
          <b-nav-item  @click="$router.push('/login')">Войти</b-nav-item>

        </b-navbar-nav>

        <b-navbar-nav v-if="currentUser" class="ml-auto">
          <template>
            <b-icon-person-square variant="light" font-scale="1.5" shift-v="-5" @click="$router.push('/profile')"></b-icon-person-square>
          </template>
          <b-nav-item  @click="$router.push('/profile')">{{ currentUser.username }}</b-nav-item>
          <template>
            <b-icon-box-arrow-right variant="light" font-scale="1.8" shift-v="-3"  @click.prevent="logOut"></b-icon-box-arrow-right>
          </template>
          <b-nav-item  @click.prevent="logOut">Выйти</b-nav-item>
        </b-navbar-nav>



      </b-collapse>
    </b-navbar>
    <div class="container">
    <router-view />
    </div>
    <footer class="footer">
      <b-row>
        <b-col lg="3">

      </b-col>
        <b-col lg="6">
          <b-icon-chat-dots variant="dark" font-scale="1.5" shift-v="-3"  @click.prevent="logOut"></b-icon-chat-dots>
        </b-col>
        <b-col lg="3">

        </b-col>
      </b-row>
    </footer>
  </div>

  <!--<div id="app">-->
    <!--<nav class="navbar navbar-expand navbar-dark bg-dark">-->
      <!--<a href class="navbar-brand" @click.prevent>bezKoder</a>-->
      <!--<div class="navbar-nav mr-auto">-->
        <!--<li class="nav-item">-->
          <!--<router-link to="/home" class="nav-link">-->
            <!--<font-awesome-icon icon="home" />Home-->
          <!--</router-link>-->
        <!--</li>-->


        <!--<li v-if="showAdminBoard" class="nav-item">-->
          <!--<router-link to="/admin" class="nav-link">Admin Board</router-link>-->
        <!--</li>-->
        <!--<li v-if="showModeratorBoard" class="nav-item">-->
          <!--<router-link to="/mod" class="nav-link">Moderator Board</router-link>-->
        <!--</li>-->
        <!--<li class="nav-item">-->
          <!--<router-link v-if="currentUser" to="/user" class="nav-link">User</router-link>-->
        <!--</li>-->
      <!--</div>-->



      <!--<div v-if="!currentUser" class="navbar-nav ml-auto">-->
        <!--<li class="nav-item">-->
          <!--<router-link to="/register" class="nav-link">-->
            <!--<font-awesome-icon icon="user-plus" />Sign Up-->
          <!--</router-link>-->
        <!--</li>-->

        <!--<li class="nav-item">-->
          <!--<router-link to="/login" class="nav-link">-->
            <!--<font-awesome-icon icon="sign-in-alt" />Login-->
          <!--</router-link>-->
        <!--</li>-->
      <!--</div>-->


      <!--<div v-if="currentUser" class="navbar-nav ml-auto">-->
        <!--<li class="nav-item">-->
          <!--<router-link to="/profile" class="nav-link">-->
            <!--<font-awesome-icon icon="user" />-->
            <!--{{ currentUser.username }}-->
          <!--</router-link>-->
        <!--</li>-->
        <!--<li class="nav-item">-->
          <!--<a class="nav-link" href @click.prevent="logOut">-->
            <!--<font-awesome-icon icon="sign-out-alt" />LogOut-->
          <!--</a>-->
        <!--</li>-->
      <!--</div>-->
    <!--</nav>-->

    <!--<div class="container">-->
      <!--<router-view />-->
    <!--</div>-->
  <!--</div>-->
</template>

<script>

    import 'bootstrap/dist/css/bootstrap.css'
    import 'bootstrap-vue/dist/bootstrap-vue.css'

    export default {
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },
            showAdminBoard() {
                // console.log("Current user is: " + this.currentUser.employeeRole[0].rolename)
                if (this.currentUser && this.currentUser.employeeRole[0].rolename === 'ROLE_ADMIN') {
                    return true;
                }

                return false;
            },
            showModeratorBoard() {
                if (this.currentUser && this.currentUser.roles) {
                    return this.currentUser.roles.includes('ROLE_MODERATOR');
                }

                return false;
            }
        },
        methods: {
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        }
    };
</script>

<style>
  html, body {
    height: 100%;
  }
  #app {
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  .container {
    flex: 1 0 auto;
  }
  .mPage_app {
    margin: 10px 5px 5px 5px;
    color: red;
    font-family: Arial;
    font-size: medium;
  }

  .mPage {
    margin: 10px 10px 50px 10px;
    font-size: large;
  }

  .main_first {
    margin: 0px 0px 50px 0px;
    font-size: larger;
  }

  .xs_btn {
    margin: 10px 0 20px 10px;
    text-align: center;
    width: 110px;
    font-size: smaller;
  }

  .sm_btn {
    margin: 10px 0 20px 10px;
    text-align: center;
    width: 110px;
    font-size: smaller;
  }

  .long_btn {
    margin: 10px 0 20px 50px;
    text-align: center;
    width: 230px;
    font-size: smaller;
  }

  .ls_btn {
    margin: 10px 0 20px 10px;
    text-align: center;
    width: 130px;
    font-size: smaller;
  }

  .err_btn {
    margin: 10px 0 20px 10px;
    text-align: center;
    width: 180px;
    font-size: smaller;
  }

  .b_table {
    text-align: center;
    font-size: smaller;
  }

  .b-icon.bi {
    display: inline-block;
    overflow: visible;
    vertical-align: 0;
  }

  .footer {
    flex-shrink: 0;
    /* Prevent Chrome, Opera, and Safari from letting these items shrink to smaller than their content's default minimum size. */
    padding: 15px;
  }

  footer {
    background: #e9ecef;
    color: black;
  }

</style>