<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <b-overlay :show="busy" rounded="lg" opacity="0.6" z-index="40">
            <template #overlay>
                <div class="d-flex align-items-center">
                    <b-spinner small type="grow" variant="secondary"></b-spinner>
                    <b-spinner type="grow" variant="dark"></b-spinner>
                    <b-spinner small type="grow" variant="secondary"></b-spinner>
                    <!-- We add an SR only text for screen readers -->
                    <span class="sr-only">Please wait...</span>
                </div>
            </template>


            <b-jumbotron>
                <b-row class="mPageTitle">
                    <!--<b-col>#{{this.$route.params.Pid.taskId}} Задача "{{this.$route.params.Pid.title}}"</b-col>-->
                    <b-col>#{{this.currentTask.taskId}} Задача "{{this.currentTask.title}}"</b-col>
                </b-row>
                <p>

                </p>


                <b-row>
                    <b-col lg="2">
                        <b-row>
                            <b-col class = "task-bold-letter">
                                {{this.getCurrentManager}}
                            </b-col>
                        </b-row>

                        <b-row>
                            <b-col class="task-sm-letter">
                                Постановщик
                            </b-col>
                        </b-row>
                    </b-col>

                    <b-col lg="2">
                        <b-row>
                            <b-col class = "task-bold-letter">
                                {{this.getCurrentExecutor}}
                            </b-col>
                        </b-row>

                        <b-row>
                            <b-col class="task-sm-letter">
                                Исполнитель
                            </b-col>
                        </b-row>
                    </b-col>


                    <b-col lg="2">
                        <b-row>
                            <b-col class = "task-bold-letter">
                                {{this.currentTask.startDate}}
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col class="task-sm-letter">
                                Старт
                            </b-col>
                        </b-row>
                    </b-col>
                    <b-col lg="2">
                        <b-row>
                            <b-col class = "task-bold-letter">
                                {{this.currentTask.finishDate}}
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col class="task-sm-letter">
                                Дедлайн
                            </b-col>
                        </b-row>
                    </b-col>
                    <b-col lg="1">
                    </b-col>
                    <b-col lg="3">
                        <b-row>
                            <b-col class="task-bold-letter">
                               {{this.currentTask.statusUpdateDate}}
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col class="task-sm-letter">
                                Статус: "{{this.currentTask.status}}"
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>

                <hr class="my-4">

                <b-row>
                    <b-col class="task-bold-letter">
                        {{this.currentTask.description}}
                    </b-col>
                </b-row>

                <hr class="my-4">

                <b-row>
                    <b-col lg="4">
                    </b-col>
                    <b-col lg="3">

                    </b-col>

                    <b-col lg="5 justify-content-end d-flex">
                        <b-button class="task_ls_btn"
                                  @click="taskStart"
                                  variant="success"
                                  size="lg"
                        >
                            <!--<b-icon icon="power" aria-hidden="true"></b-icon>-->
                            Начать
                        </b-button>
                        <b-button class="task_fl_btn"
                                  @click="taskPause"
                                  variant="success"
                                  size="lg"
                        >Приостановить
                        </b-button>
                        <b-button class="task_mid_btn"
                                  @click="taskEnd"
                                  variant="success"
                                  size="lg"
                        >Закончить
                        </b-button>
                    </b-col>
                </b-row>


            </b-jumbotron>


            <b-toast
                    id="success-toast"
                    variant="success"
                    solid
                    :append-toast=true
                    toaster='b-toaster-bottom-right'
            >
                <template v-slot:toast-title>
                    <div class="d-flex flex-grow-1 align-items-baseline">
                        <b-img blank blank-color="#8FBC8F" class="mr-2" width="12" height="12"></b-img>
                        <strong class="mr-auto">Успешно!</strong>
                    </div>
                </template>
                {{ this.message }}
            </b-toast>

            <b-toast
                    id="warning-toast"
                    variant="warning"
                    solid
                    :append-toast=true
                    toaster='b-toaster-bottom-right'
            >
                <template v-slot:toast-title>
                    <div class="d-flex flex-grow-1 align-items-baseline">
                        <b-img blank blank-color="#FF8C00" class="mr-2" width="12" height="12"></b-img>
                        <strong class="mr-auto">Предупреждение!</strong>
                    </div>
                </template>
                {{ this.message }}
            </b-toast>

            <b-toast
                    id="danger-toast"
                    variant="danger"
                    solid
                    :append-toast=true
                    toaster='b-toaster-bottom-right'
            >
                <template v-slot:toast-title>
                    <div class="d-flex flex-grow-1 align-items-baseline">
                        <b-img blank blank-color="#8B0000" class="mr-2" width="12" height="12"></b-img>
                        <strong class="mr-auto">Ошибка!</strong>
                    </div>
                </template>
                {{ this.message }}
            </b-toast>
        </b-overlay>
    </div>
</template>

<script>

    import 'bootstrap/dist/css/bootstrap.css'
    import 'bootstrap-vue/dist/bootstrap-vue.css'
    import axios from 'axios'

    export default {
        name: 'UserTasks',
        watch: {
            $route: {
                immediate: true,
                handler() {
                    document.title = 'Задача [#' + this.$route.params.Pid + ']' || 'TELPTracker';
                }
            },
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },

            getCurrentExecutor() {
                let user;
                this.executors.forEach(function(item) {
                    if(item.role === "ИСПОЛНИТЕЛЬ") {
                        user =  item.surname
                    }
                });
                return user
            },

            getCurrentManager() {
                let user;
                this.executors.forEach(function(item) {
                    if(item.role === "ПОСТАНОВЩИК") {
                        user =  item.surname
                    }
                });
                return user
            },

            rows() {
                return this.postsTask.length
            },

        },
        mounted() {
            if (!this.currentUser) {
                this.$router.push('/login');
            }
        },


        data() {
            return {
                postsTask: [],
                messageTask: '',
                message: '',
                taskTitle: '',
                taskDescription: '',
                currentExecutor: '',
                currentManager: '',
                taskStartDate: '',
                taskFinishDate: '',
                executors: [],
                currentTask:'',

                isDeletePopup: false,
                currentPage: 1,
                perPage: 5,
                currentId:'',

                checkboxSelected: false,
                disableState: true,
                rowSelected: '',
                isInfoPopupVisible: false,
                files: [],
                items: [],
                selectMode: 'single',
                messageView: false,
                boxOne: '',
                busy: false,
            }
        },
        methods: {

            getTaskMembers() {
                this.busy = true;
                let id = this.$route.params.Pid;
                console.log('taskId for members', id);
                axios.post('/api/tasks/members/' + id,
                ).then(response => {
                    console.log('taskMembers', response.data);
                    this.executors = response.data;
                }).catch(error => {
                    console.log(error)
                }).finally(() => {
                    this.busy = false
                })
            },

            getTaskById() {
                this.busy = true;
                let id = this.$route.params.Pid;
                axios.post('/api/tasks/current/' + id,
                ).then(response => {
                    this.currentTask = response.data;
                    console.log("currentTask" + this.currentTask)
                }).catch(error => {
                    console.log(error)
                }).finally(() => {
                    this.busy = false
                })
            },


            onRowSelected(items) {
                this.selected = items
                console.log(this.selected);
                if (this.selected.length === 0) {
                    this.disableState = true
                } else {
                    this.disableState = false
                }
            },


            taskStart() {
                this.busy = true
                let id = this.$route.params.Pid;
                console.log('taskId=', id);
                axios.post('/api/tasks/status/' + id + '/start',
                ).then(response => {
                    console.log('success', response.data)
                    this.message = "Статус задачи изменен"
                    this.$bvToast.show('success-toast')
                    this.getTaskById()
                }).catch(error => {
                    console.log(error)
                    this.message = "Не удалось изменить статус!"
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            taskPause() {
                this.busy = true
                let id = this.$route.params.Pid;
                console.log('taskId=', id);
                axios.post('/api/tasks/status/' + id + '/pause',
                ).then(response => {
                    console.log('success', response.data)
                    this.message = "Статус задачи изменен"
                    this.$bvToast.show('success-toast')
                    this.getTaskById()
                }).catch(error => {
                    console.log(error)
                    this.message = "Не удалось изменить статус!"
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            taskEnd() {
                this.busy = true
                let id = this.$route.params.Pid;
                console.log('taskId=', id);
                axios.post('/api/tasks/status/' + id + '/end',
                ).then(response => {
                    console.log('success', response.data)
                    this.message = "Статус задачи изменен"
                    this.$bvToast.show('success-toast')
                    this.getTaskById()
                }).catch(error => {
                    console.log(error)
                    this.message = "Не удалось изменить статус!"
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },



        },

        beforeMount() {
            this.getTaskMembers()
            this.getTaskById()
        },

        beforeDestroy() {
        },
    };


</script>

<style>
    .mPageTitle {
        font-family: Arial;
        margin: 10px 10px 50px 10px;
        font-size: 28px;
        text-align: center;

    }

    .task-sm-letter {
        font-family: Arial;
        /*margin: 10px 10px 20px 10px;*/
        font-size: 14px;
    }

    .task-bold-letter {
        font-family: Arial;
        /*margin: 10px 10px 20px 10px;*/
        font-size: 14px;
        font-weight: bold;
    }

    .mPageModal {
        font-family: Arial;
        margin: 10px 10px 10px 10px;
        font-size: 18px;
    }

    .mPageModalRow {
        margin: 0 0 10px 0;
    }

    .task_ls_btn {
        margin: 0 0 0 10px;
        text-align: center;
        width: 90px;
        font-family: Arial;
        font-size: 14px;
    }

    .task_sh_btn {
        margin: 10px 0 0 30px;
        text-align: center;
        width: 100px;
        font-family: Arial;
        font-size: 14px;
    }

    .b_table thead {
        background-color: white;
        border: 3px solid limegreen !important;
    }

    .b_table tbody {
        background-color: white;
        border: 3px solid limegreen !important;
    }


    .page-item.active .page-link {
        z-index: 3;
        color: #fff;
        background-color: limegreen !important;
        border-color: limegreen !important;
    }


    .page-link {
        position: relative;
        display: block;
        padding: 0.5rem 0.75rem;
        margin-left: -1px;
        line-height: 1.25;
        color: black !important;
        background-color: #fff;
        border: 1px solid #dee2e6;
    }

</style>