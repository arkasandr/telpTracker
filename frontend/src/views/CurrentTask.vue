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


            <div class="task-card">
                <b-card bg-variant="light" border-variant="light">
                    <b-row class="mPageTitle">
                        <!--<b-col>#{{this.$route.params.Pid.taskId}} Задача "{{this.$route.params.Pid.title}}"</b-col>-->
                        <b-col>#{{this.currentTask.taskId}} Задача "{{this.currentTask.title}}"</b-col>
                    </b-row>
                    <p>

                    </p>


                    <b-row>
                        <b-col lg="2">
                            <b-row>
                                <b-col class="task-bold-letter">
                                    {{this.getCurrentManager[0] + " " + this.getCurrentManager[1]}}
                                </b-col>
                            </b-row>

                            <b-row>
                                <b-col class="task-sm-letter">
                                    Постановщик
                                </b-col>
                            </b-row>
                        </b-col>

                        <b-col lg="3">
                            <b-row>
                                <b-col class="task-bold-letter">
                                    {{this.getCurrentExecutor[0] + " " + this.getCurrentExecutor[1]}}
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
                                <b-col class="task-bold-letter">
                                    {{this.currentTask.startDate}}
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col class="task-sm-letter">
                                    Начало
                                </b-col>
                            </b-row>
                        </b-col>
                        <b-col lg="2">
                            <b-row>
                                <b-col class="task-bold-letter">
                                    {{this.currentDeadLine}}
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col class="task-sm-letter">
                                    Окончание
                                </b-col>
                            </b-row>
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
                            <b-button class="task_mid_btn"
                                      @click="taskChange"
                                      variant="success"
                                      size="lg"
                                      v-if="currentUser && currentUser.employeeRole[0].rolename === 'ROLE_ADMIN'"
                            >Изменить
                            </b-button>
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
                </b-card>
            </div>


            <div class="task-card">
                <b-card bg-variant="light" border-variant="light">
                    <div>
                        <b-row>
                            <!--<b-col lg="2">-->
                            <!--<b-button class="task_fl_btn"-->
                            <!--variant="success"-->
                            <!--size="lg"> Прикрепить-->
                            <!--<b-icon-paperclip variant="light" font-scale="1.4" shift-v="-2"  ></b-icon-paperclip>-->
                            <!--</b-button>-->
                            <!--</b-col>-->
                            <b-col lg="1">
                                <b-button class="task_xs_btn"
                                          variant="white"
                                          size="lg"
                                          id="add-file-button"
                                >
                                    <b-icon-paperclip font-scale="2.3" shift-v="-1"></b-icon-paperclip>
                                </b-button>
                                <b-tooltip target="add-file-button" placement="bottom" variant="dark">
                                    Прикрепить
                                </b-tooltip>
                            </b-col>
                            <b-col lg="10">
                                <b-form-textarea
                                        id="input-live-description"
                                        v-model="messageBody"
                                        aria-describedby="input-live-description-help input-live-description-feedback"
                                        placeholder="Напишите сообщение"
                                        trim
                                ></b-form-textarea>
                            </b-col>
                            <b-col lg="1">
                                <b-button class="task_xs_btn"
                                          variant="white"
                                          size="lg"
                                          id="send-button"
                                          @click="addNewMessage"
                                          :disabled="this.sendMessageText"
                                          v-b-tooltip.focus
                                >
                                    <b-icon-reply font-scale="2.3"></b-icon-reply>
                                </b-button>
                                <b-tooltip ref="sendTooltip" target="send-button" placement="bottom" variant="dark">
                                    Отправить
                                </b-tooltip>
                            </b-col>
                        </b-row>

                        <b-row>
                            <b-col class="time-spend">
                                Потрачено
                            </b-col>

                        </b-row>
                        <b-row>
                            <b-col lg="1">
                                Когда
                            </b-col>
                            <b-col lg="5">

                                <b-form-datepicker
                                        v-model="spendDate"
                                        placeholder="Выберите дату" locale="ru"
                                ></b-form-datepicker>
                            </b-col>
                            <b-col lg="5">
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                Сколько
                            </b-col>
                            <b-col lg="5">

                                <b-form-timepicker
                                        v-model="timeSpendValue"
                                        v-bind="labels[locale] || {}"
                                        :locale="locale"
                                ></b-form-timepicker>
                            </b-col>
                            <b-col lg="5">
                            </b-col>
                            <b-col>

                            </b-col>
                        </b-row>
                    </div>
                </b-card>
            </div>

            <div>
                <b-card bg-variant="light" border-variant="light">
                    <div>
                        <b-table
                                id="single-task-table"
                                class="b_message_table"
                                ref="messageTable"
                                selectable
                                :select-mode="selectMode"
                                :items="singleTask"
                                :fields="singleTaskFields"
                                responsive="sm"
                                thead-class="hidden_header"
                        >

                            <template
                                    v-slot:cell(members)="data"
                            >
                                {{ data.item.members.join(', ')}}
                            </template>

                            <template
                                    v-slot:cell(text)="data"
                            >
                                <b-row>
                                    <b-col class="message-header">
                                        {{ moment(data.item.spendDate).format('dddd, D MMMM YYYY')}}
                                    </b-col>
                                </b-row>
                                <b-row>
                                    <b-col class="task-bold-letter">
                                        {{ data.item.sender}},   {{ moment(data.item.createTime, "HH:mm:ss").format("HH ч:mm мин")}}
                                    </b-col>
                                </b-row>
                                <b-row>
                                    <b-col>
                                        {{ data.item.text}}
                                    </b-col>
                                </b-row>
                                <b-row>
                                    <b-col lg="2" v-if="data.item.spendTime !== null">
                                        <b-icon-alarm font-scale="0.8"></b-icon-alarm>
                                        {{moment(data.item.spendTime, "HH:mm:ss").format("hh:mm")}}
                                    </b-col>
                                    <b-col lg="1">
                                    </b-col>
                                    <b-col>

                                    </b-col>
                                </b-row>
                            </template>

                        </b-table>
                    </div>
                </b-card>
            </div>



            <div>
                <b-modal id="bv-modal-task-change" size="lg" hide-footer :no-close-on-backdrop="true">
                    <template v-slot:modal-title>
                        Внесите изменения
                    </template>
                    <div class="mPageModal">
                        <b-row>
                            <b-col lg="2">

                            </b-col>
                            <b-col class="task-update-st-letter">
                                Текущее значение
                            </b-col>
                            <b-col lg="4" class="task-update-st-letter">
                                Новое значение
                            </b-col>

                        </b-row>
                        <b-row>
                            <b-col >
                                <p></p>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col lg="2" class="task-update-st-letter">
                                Исполнитель
                            </b-col>
                            <b-col class="task-update-sm-letter">
                                {{this.currentTaskExecutor}}
                            </b-col>
                            <b-col lg="4">
                                <b-form-input
                                        v-model="newTaskExecutor"
                                        placeholder="Выберите исполнителя"
                                        list="taskExecutors-list"
                                ></b-form-input>
                                <b-form-datalist id="taskExecutors-list" :options="taskExecutors">
                                </b-form-datalist>
                            </b-col>

                        </b-row>
                        <b-row>
                            <b-col >
                                <p></p>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col lg="2" class="task-update-st-letter">
                                Дедлайн
                            </b-col>
                            <b-col class="task-update-sm-letter">
                                {{this.currentFinishDate}}
                            </b-col>
                            <b-col lg="4">
                                <template>
                                    <div>
                                        <b-form-datepicker id="task-end-datepicker" v-model="newTaskFinishDate"
                                                           placeholder="Выберите дату" locale="ru"
                                                           class="mb-2"></b-form-datepicker>
                                    </div>
                                </template>
                            </b-col>

                        </b-row>
                    </div>
                    <b-row>
                        <b-col lg="3">
                        </b-col>
                        <b-col>
                        </b-col>
                        <b-col lg="5">
                            <b-button class="task_sh_btn"
                                      @click="updateCurrentTask"
                                      variant="success"
                                      size="sm"
                                      :disabled="disableUpdateTaskDetails"
                            >
                                Сохранить
                            </b-button>
                            <b-button class="task_sh_btn"
                                      @click="$bvModal.hide('bv-modal-task-change')"
                                      variant="danger"
                                      size="sm">
                                Отмена
                            </b-button>
                        </b-col>

                    </b-row>
                </b-modal>
            </div>


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
    import axios from 'axios'
    import moment from 'moment'
    import 'moment-timezone'


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
                let user = [];
                this.executors.forEach(function (item) {
                    if (item.role === "ИСПОЛНИТЕЛЬ") {
                        user = [item.surname, item.firstName, item.middleName]
                    }
                });
                return user
            },


            getCurrentManager() {
                let user = [];
                this.executors.forEach(function (item) {
                    if (item.role === "ПОСТАНОВЩИК") {
                        user = [item.surname, item.firstName]
                    }
                });
                return user
            },

            rows() {
                return this.postsTask.length
            },

            sendMessageText() {
                return this.messageBody.length === 0 || this.spendDate.length === 0
            },

            disableUpdateTaskDetails() {
                return this.newTaskExecutor.length === 0 && this.newTaskFinishDate.length === 0
            },

        },
        mounted() {
            if (!this.currentUser) {
                this.$router.push('/login');
            }
        },


        data() {
            return {
                singleTask: [],
                messageTask: '',
                message: '',
                taskTitle: '',
                taskDescription: '',
                currentExecutor: '',
                currentManager: '',
                taskStartDate: '',
                taskFinishDate: '',
                taskExecutors: [],
                currentTask: '',
                messageBody: '',
                locale: 'de',
                labels: {
                    de: {
                        labelHours: 'Часы',
                        labelMinutes: 'Минуты',
                        labelSeconds: 'Секунды',
                        labelIncrement: 'Erhöhen',
                        labelDecrement: 'Verringern',
                        labelSelected: 'Ausgewählte Zeit',
                        labelNoTimeSelected: 'Введите время',
                        labelCloseButton: 'Применить'
                    }
                },

                timeSpendValue: '',
                spendDate: '',
                singleTaskFields: [
                    {key: 'text', label: 'Текст', sortable: true},
                ],
                sender: '',
                singleMessage: '',
                disableSendMessageState: true,


                isDeletePopup: false,
                currentPage: 1,
                perPage: 5,
                currentId: '',

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

                currentTaskId: '',
                currentFinishDate: '',
                currentTaskExecutor: '',
                taskExecutor: '',
                newTaskExecutor: '',
                newTaskFinishDate: '',
                employeeByFio:'',
                currentDeadLine:'',
            }
        },
        methods: {

            getTaskMembers() {
                this.busy = true;
                let id = this.$route.params.Pid;
                axios.post('/api/tasks/members/' + id,
                ).then(response => {
                    console.log('success', response.data);
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
                    this.currentDeadLine = response.data.finishDate;
                    console.log("currentTask" + this.currentTask)
                }).catch(error => {
                    console.log(error)
                }).finally(() => {
                    this.busy = false
                })
            },


            onRowSelected(items) {
                this.selected = items;
                console.log(this.selected);
                if (this.selected.length === 0) {
                    this.disableState = true
                } else {
                    this.disableState = false
                }
            },


            taskStart() {
                this.busy = true;
                let id = this.$route.params.Pid;
                axios.post('/api/tasks/status/' + id + '/start',
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Статус задачи изменен";
                    this.$bvToast.show('success-toast');
                    this.getTaskById()
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось изменить статус!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.getAllMessages();
                    this.busy = false
                })
            },

            taskPause() {
                this.busy = true;
                let id = this.$route.params.Pid;
                axios.post('/api/tasks/status/' + id + '/pause',
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Статус задачи изменен";
                    this.$bvToast.show('success-toast');
                    this.getTaskById()
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось изменить статус!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.getAllMessages();
                    this.busy = false
                })
            },

            taskEnd() {
                this.busy = true;
                let id = this.$route.params.Pid;
                console.log('taskId=', id);
                axios.post('/api/tasks/status/' + id + '/end',
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Статус задачи изменен";
                    this.$bvToast.show('success-toast');
                    this.getTaskById()
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось изменить статус!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.getAllMessages();
                    this.busy = false
                })
            },

            addNewMessage() {
                this.busy = true;
                console.log('timeSpendValue', moment.tz(this.timeSpendValue, "America/New_York"));
                axios.post('/api/tasks/message/add',
                    {
                        text: this.messageBody,
                        spendTime: this.timeSpendValue,
                        spendDate: this.spendDate,
                        taskNumber: this.currentTask.taskId
                    },
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Новый комментарий добавлен!";
                    this.$bvToast.show('success-toast')
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось добавить комментарий!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.messageBody = '';
                    this.spendDate = '';
                    this.timeSpendValue = '';
                    this.$refs.sendTooltip.$emit('close');
                    this.getAllMessages();
                    this.busy = false
                })
            },

            getAllMessages() {
                this.busy = true;
                let id = this.$route.params.Pid;
                axios.post('/api/tasks/message/' + id + '/getall'
                ).then(response => {
                    console.log('success', response.data);
                    this.messageTask = "Все комментарии загружены";
                    this.singleTask = response.data;
                    console.log('allMessages', this.singleTask);
                    this.singleTask.forEach(element =>
                            Object.assign(element, this.getSenderByTaskMessageId(element.messageId)),
                    );
                    this.$bvToast.show('success-toast')
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось загрузить комментарии!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },


            getSenderByTaskMessageId(id) {
                let sender;
                console.log('this.singleTask.messageId', id);
                this.busy = true;
                axios.post('/api/tasks/message/' + id + '/getsender'
                ).then(response => {
                    console.log('sender after response', {sender: response.data.surname});
                    let fi = response.data.surname + ' ' + response.data.firstName;
                    sender = {sender: fi};
                    for (let i of this.singleTask) {
                        if (i.messageId === id) {
                            Object.assign(i, sender);
                                console.log('allModifyMessages', this.singleTask)
                        }
                    }
                }).catch(error => {
                    console.log(error)
                }).finally(() => {
                    this.$refs.messageTable.refresh();
                });
                return sender
            },


            getAllEmployees() {
                this.busy = true;
                axios.get('/api/employee/getall'
                ).then(response => {
                    console.log('success', response.data);
                    this.taskExecutors = response.data
                }).catch(error => {
                    console.log(error)
                }).finally(() => {
                    this.busy = false
                })
            },

            taskChange() {
                this.currentTaskId = this.currentTask.taskId;
                this.currentFinishDate = this.currentTask.finishDate;
                this.currentTaskExecutor = this.getCurrentExecutor[0] + ' ' + this.getCurrentExecutor[1] + ' ' + this.getCurrentExecutor[2];
                this.getAllEmployees()
                this.$bvModal.show('bv-modal-task-change')
            },

            updateCurrentTask() {
                if(this.newTaskExecutor.length !== 0) {
                    let arr = this.newTaskExecutor.split(' ');
                    axios.post('/api/employee/getbyfio',
                        arr,
                    ).then(response => {
                        this.employeeByFio = response.data;
                        console.log('success employeeByFio', response.data);
                        this.updateTask()
                    }).catch(error => {
                        console.log(error)
                    }).finally(() => {
                    })
                } else {
                    this.updateTask()
                }
            },

            updateTask() {
                if(this.employeeByFio.length !== 0) {
                    this.busy = true;
                    let id = this.currentTaskId;
                    let date = this.newTaskFinishDate;
                    let arr = [this.employeeByFio];
                    axios.post('/api/tasks/admin/' + id + '/update',
                        {
                            finishDate: date,
                            members: arr
                        }, id
                    ).then(response => {
                        console.log('success', response.data);
                        this.getCurrentExecutor[0] = this.employeeByFio.surname;
                        this.getCurrentExecutor[1] = this.employeeByFio.firstName;
                        this.message = "Задача обновлена!";
                        this.$bvToast.show('success-toast');
                        this.$bvModal.hide('bv-modal-task-change')
                    }).catch(error => {
                        console.log(error);
                        this.message = "Не удалось обновить задачу!";
                        this.$bvToast.show('danger-toast')
                    }).finally(() => {
                        this.refreshUpdateTaskInputs();
                        this.getAllMessages();
                        this.busy = false
                    })
                } else {
                    this.busy = true;
                    let id = this.currentTaskId;
                    let date = this.newTaskFinishDate;
                    let arr = [null];
                    axios.post('/api/tasks/admin/' + id + '/update',
                        {
                            finishDate: date,
                            members: arr
                        }, id
                    ).then(response => {
                        console.log('success', response.data);
                        this.currentDeadLine = this.newTaskFinishDate;
                        this.message = "Задача обновлена!";
                        this.$bvToast.show('success-toast');
                        this.$bvModal.hide('bv-modal-task-change')
                    }).catch(error => {
                        console.log(error);
                        this.message = "Не удалось обновить задачу!";
                        this.$bvToast.show('danger-toast')
                    }).finally(() => {
                        this.refreshUpdateTaskInputs();
                        this.getAllMessages();
                        this.busy = false
                    })
                }
            },

            refreshUpdateTaskInputs() {
                this.newTaskExecutor = '';
                this.newTaskFinishDate = '';
                this.employeeByFio = ''
            },
        },


        beforeMount() {
            this.getTaskMembers();
            this.getTaskById();
            this.getAllMessages()

        },

        beforeDestroy() {
        },
    };


</script>

<style>
    .mPageTitle {
        font-family: Arial;
        margin: 0 10px 50px 10px;
        font-size: 28px;
        text-align: center;

    }

    .task-card {
        margin: 0 0 20px 0;
    }

    .time-spend {
        font-family: Arial;
        margin: 20px 10px 10px 90px;
        font-size: 16px;
        font-weight: bold;
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
        margin: 0 0 0 0;
        text-align: center;
        width: 90px;
        font-family: Arial;
        font-size: 14px;
    }

    .task_xs_btn {
        margin: 0 0 0 0;
        text-align: center;
        width: 60px;
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

    /*.b_table thead {*/
    /*background-color: white;*/
    /*border: 3px solid limegreen !important;*/
    /*}*/

    /*.b_table tbody {*/
    /*background-color: white;*/
    /*border: 3px solid limegreen !important;*/
    /*}*/


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

    .bg-light {
        background-color: #e9ecef !important;
    }

    .hidden_header {
        display: none;
    }

    .message-header {
        font-family: Arial;
        /*margin: 10px 10px 20px 10px;*/
        font-size: 14px;
        text-align: center;
        color: grey;

    }

</style>