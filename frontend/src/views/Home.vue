<template>
    <div class="container">
        <router-view></router-view>
        <!--<header class="jumbotron">-->
        <!--<h3>{{content}}</h3>-->
        <!--</header>-->
        <b-jumbotron>
            <template #header>TELPTracker</template>
            <p>


            </p>
            <template #lead>
                Этот сервис позволит Вам организовать работу инженеров в группе, вести учет выданных заданий и
                получать дополнительную информацию о проделанной работе.
            </template>

            <hr class="my-4">

            <p>
                Переданная информация остается конфиденциальной.
            </p>
            <p>
                Продолжая работу с сервисом, Вы даете согласие на хранение и обработку персональных данных.
            </p>
            <!--<b-button variant="primary" href="#">Do Something</b-button>-->
            <!--<b-button variant="success" href="#">Do Something Else</b-button>-->
        </b-jumbotron>
    </div>
</template>

<script>
    import UserService from '../services/user.service';

    export default {
        name: 'Home',
        watch: {
            $route: {
                immediate: true,
                handler(to) {
                    document.title = to.meta.title || 'TELPTracker';
                }
            },
        },

        data() {
            return {
                content: ''
            };
        },

        mounted() {
            UserService.getPublicContent().then(
                response => {
                    this.content = response.data;
                },
                error => {
                    this.content =
                        (error.response && error.response.data) ||
                        error.message ||
                        error.toString();
                }
            );
        }
    };
</script>