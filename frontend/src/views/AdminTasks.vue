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
                    <b-col></b-col>
                    <b-col>Диспетчер задач</b-col>
                    <b-col></b-col>
                </b-row>
                <p>

                </p>


                <b-row class="ml-auto">
                    <b-col lg="5">
                    </b-col>
                    <b-col lg="4">
                    </b-col>
                    <b-col lg="3 justify-content-end d-flex">
                        <b-button v-if="currentUser && currentUser.employeeRole[0].rolename === 'ROLE_ADMIN'"
                                  class="task_lt_btn"
                                  @click="addNewTask" variant="success" size="lg"
                        >Добавить
                        </b-button>
                        <b-button v-if="currentUser && currentUser.employeeRole[0].rolename === 'ROLE_ADMIN'"
                                  class="task_lt_btn"
                                  @click="deleteCurrentTask" :disabled="this.disableState"
                                  variant="danger" size="lg">Удалить
                        </b-button>

                    </b-col>
                </b-row>

                <!--<b-row>-->
                <!--<b-col lg="4">-->
                <!--<b-form-group-->
                <!--&gt;-->


                <!--<b-input-group size="sm">-->
                <!--<b-input-group-append>-->
                <!--<b-button-->

                <!--@click="searchTaskByCriteria"-->
                <!--variant="success"-->
                <!--:disabled="!filter"-->
                <!--&gt;Найти-->
                <!--</b-button>-->
                <!--</b-input-group-append>-->
                <!--<b-form-input-->
                <!--id="filter-input"-->
                <!--v-model="filter"-->
                <!--placeholder="Поиск..."-->
                <!--@keydown.native="enterSubmitSearch"-->
                <!--&gt;</b-form-input>-->

                <!--<b-input-group-append>-->
                <!--<b-button-->
                <!--@click="clearSearchInput"-->
                <!--variant="danger"-->
                <!--:disabled="!filter"-->
                <!--&gt;-->
                <!--<b-icon-backspace variant="light"></b-icon-backspace>-->
                <!--</b-button>-->
                <!--</b-input-group-append>-->
                <!--</b-input-group>-->
                <!--</b-form-group>-->
                <!--</b-col>-->
                <!--<b-col lg="1">-->
                <!--</b-col>-->
                <!--<b-col lg="4">-->
                <!--<b-input-group size="sm">-->
                <!--<b-input-group-append>-->
                <!--<b-button-->

                <!--@click="searchTaskByCriteria"-->
                <!--variant="success"-->
                <!--:disabled="!filter"-->
                <!--size="sm"-->
                <!--&gt;Найти за период-->
                <!--</b-button>-->
                <!--</b-input-group-append>-->
                <!--<b-button-group-append>-->
                <!--<b-form-datepicker id="task-search-start-datepicker" v-model="taskSearchStartDate"-->
                <!--placeholder="Дата" locale="ru"-->
                <!--size="sm"-->
                <!--:date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }">-->
                <!--</b-form-datepicker>-->
                <!--</b-button-group-append>-->
                <!--<b-button-group-append>-->
                <!--<b-form-datepicker id="task-search-end-datepicker" v-model="taskSearchEndDate"-->
                <!--placeholder="Дата" locale="ru"-->
                <!--size="sm"-->
                <!--:date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }">-->

                <!--</b-form-datepicker>-->
                <!--</b-button-group-append>-->
                <!--<b-button-group-append>-->
                <!--<b-button-->
                <!--@click="clearSearchInput"-->
                <!--variant="danger"-->
                <!--:disabled="!filter"-->
                <!--size="sm"-->
                <!--&gt;-->
                <!--<b-icon-backspace variant="light"></b-icon-backspace>-->
                <!--</b-button>-->
                <!--</b-button-group-append>-->
                <!--</b-input-group>-->
                <!--</b-col>-->
                <!--</b-row>-->
                <b-row class="ml-auto">
                    <div>
                        <b-tabs content-class="mt-3"
                                active-nav-item-class="font-weight-bold text-success"

                        >
                            <b-tab title="Поиск по ключевым словам" active>

                                <b-row>
                                    <b-col lg="12">
                                        <b-form-group
                                        >
                                            <b-input-group size="sm">
                                                <b-input-group-append>
                                                    <b-button

                                                            @click="searchTaskByCriteria"
                                                            variant="success"
                                                            :disabled="!filter"
                                                    >Найти
                                                    </b-button>
                                                </b-input-group-append>
                                                <b-form-input
                                                        id="filter-input"
                                                        v-model="filter"
                                                        placeholder="Поиск..."
                                                        @keydown.native="enterSubmitSearch"
                                                ></b-form-input>

                                                <b-input-group-append>
                                                    <b-button
                                                            @click="clearSearchInput"
                                                            variant="danger"
                                                            :disabled="!filter"
                                                    >
                                                        <b-icon-backspace variant="light"></b-icon-backspace>
                                                    </b-button>
                                                </b-input-group-append>
                                            </b-input-group>
                                        </b-form-group>
                                    </b-col>

                                </b-row>
                            </b-tab>

                            <b-tab title="Поиск по датам">

                                        <b-form-group
                                        >
                                            <b-button-group size="sm">
                                                <b-button-group-append>
                                                    <b-button

                                                            @click="searchTaskByDate"
                                                            variant="success"
                                                            :disabled="!filter"
                                                            size="sm"
                                                    >Найти
                                                    </b-button>
                                                </b-button-group-append>
                                                <b-button-group-append>
                                                    <b-form-datepicker id="task-search-start-datepicker"
                                                                       v-model="taskSearchStartDate"
                                                                       placeholder="Начало периода" locale="ru"
                                                                       size="sm"
                                                                       :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }">
                                                    </b-form-datepicker>
                                                </b-button-group-append>
                                                <b-button-group-append>
                                                    <b-form-datepicker id="task-search-end-datepicker"
                                                                       v-model="taskSearchEndDate"
                                                                       placeholder="Окончание периода" locale="ru"
                                                                       size="sm"
                                                                       :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }">

                                                    </b-form-datepicker>
                                                </b-button-group-append>
                                                <b-button-group-append>
                                                    <b-button
                                                            @click="clearSearchInput"
                                                            variant="danger"
                                                            :disabled="!filter"
                                                            size="sm"
                                                    >
                                                        <b-icon-backspace variant="light"></b-icon-backspace>
                                                    </b-button>
                                                </b-button-group-append>
                                            </b-button-group>
                                        </b-form-group
                                        >
                            </b-tab>
                        </b-tabs>
                    </div>

                </b-row>


                <b-table
                        id="task-table"
                        class="b_table"
                        ref="selectableTable"
                        selectable
                        :select-mode="selectMode"
                        :items="postsTask"
                        :fields="taskFields"
                        @row-selected="onRowSelected"
                        :bordered="true"
                        responsive="sm"
                        :per-page="perPage"
                        :current-page="currentPage"
                        @row-dblclicked="(item) => onRowDoubleClick(item)"
                        @filtered="onFiltered"
                >

                    <template
                            v-slot:cell(members)="data"
                    >
                        {{ data.item.members.join(', ')}}
                    </template>

                    <!--<template v-slot:cell(taskId)>-->
                    <!--<b-button size="sm" @click="onRowDoubleClick" class="mr-1">-->
                    <!--Info modal-->
                    <!--</b-button>-->
                    <!--</template>-->

                    <template v-slot:cell(taskId)="data">
                        <!--<router-link :to="{path:'/tasks/${data.value'}">{{ data.value }}</router-link>-->
                        <!--<router-link :to="{ path:'/tasks/${data.value', params: { taskId: data.value.id} }">{{ data.value }}</router-link>-->
                        <router-link :to="{name:'currentTask', params: {Pid:data.value} }">{{ data.value }}
                        </router-link>
                    </template>

                    <template v-slot:cell(selected)="{ rowSelected }">
                        <template v-if="rowSelected">
                <span aria-hidden="true">
                  <input type="checkbox" :disabled="true"/>
                </span>
                        </template>
                        <template v-else>
                            <span aria-hidden="true">&nbsp;</span>
                        </template>
                    </template>

                </b-table>

                <b-row>
                    <b-col lg="9">

                    </b-col>
                    <b-col lg="3 justify-content-end d-flex">
                        <b-pagination
                                v-model="currentPage"
                                :total-rows="rows"
                                :per-page="perPage"
                                aria-controls="task-table"
                                size="sm"
                                class="custom_pagination"
                        >
                        </b-pagination>
                    </b-col>
                </b-row>

                <b-row>
                    <b-col lg="4">
                        <b-button
                                v-if="currentUser && currentUser.employeeRole[0].rolename === 'ROLE_ADMIN'"
                                class="task_mid_btn"
                                @click="taskChange"
                                variant="success"
                                size="lg"
                                :disabled="this.disableState"
                        >
                            <!--<b-icon icon="power" aria-hidden="true"></b-icon>-->
                            Изменить
                        </b-button>
                    </b-col>
                    <b-col lg="3">

                    </b-col>

                    <b-col lg="5 justify-content-end d-flex">
                        <b-button class="task_ls_btn"
                                  @click="taskStart" :disabled="this.disableState"
                                  variant="success" size="lg"
                        >
                            <!--<b-icon icon="power" aria-hidden="true"></b-icon>-->
                            Начать
                        </b-button>
                        <b-button class="task_fl_btn"
                                  @click="taskPause" :disabled="this.disableState"
                                  variant="success" size="lg"
                        >Приостановить
                        </b-button>
                        <b-button class="task_mid_btn"
                                  @click="taskEnd" :disabled="this.disableState"
                                  variant="success" size="lg"
                        >Закончить
                        </b-button>
                    </b-col>
                </b-row>


                <div>
                    <b-modal id="bv-modal-task" size="lg" hide-footer :no-close-on-backdrop="true">
                        <template v-slot:modal-title>
                            Добавить новую задачу
                        </template>
                        <div class="mPageModal">
                            <b-container>

                                <b-row class="mPageModalRow">
                                    <b-col sm="3" align-v="end">
                                        <label>Название</label>
                                    </b-col>
                                    <b-col sm="9">
                                        <b-form-input
                                                id="input-live-title"
                                                v-model="taskTitle"
                                                aria-describedby="input-live-title-help input-live-title-feedback"
                                                placeholder="Введите название"
                                                trim
                                        ></b-form-input>

                                    </b-col>
                                </b-row>

                                <b-row class="mPageModalRow">
                                    <b-col sm="3" align-v="end">
                                        <label>Описание</label>
                                    </b-col>
                                    <b-col sm="9">
                                        <b-form-textarea
                                                id="input-live-description"
                                                v-model="taskDescription"
                                                aria-describedby="input-live-description-help input-live-description-feedback"
                                                placeholder="Введите описание"
                                                trim
                                        ></b-form-textarea>
                                    </b-col>
                                </b-row>

                                <b-row class="mPageModalRow">
                                    <b-col sm="3" align-v="end">
                                        <label>Исполнитель</label>
                                    </b-col>
                                    <b-col sm="9">
                                        <b-form-input
                                                id="input-live-executor"
                                                v-model="taskExecutor"
                                                aria-describedby="input-live-executor-help input-live-executor-feedback"
                                                placeholder="Выберите исполнителя из списка"
                                                list="executors-list"
                                        ></b-form-input>
                                        <b-form-datalist id="executors-list" :options="executors">
                                        </b-form-datalist>
                                    </b-col>
                                </b-row>

                                <b-row class="mPageModalRow">
                                    <b-col sm="3" align-v="end">
                                        <label>Дата начала</label>
                                    </b-col>
                                    <b-col sm="9">
                                        <template>
                                            <div>
                                                <b-form-datepicker id="task-start-datepicker" v-model="taskStartDate"
                                                                   placeholder="Выберите дату" locale="ru"
                                                                   class="mb-2"></b-form-datepicker>
                                            </div>
                                        </template>

                                    </b-col>
                                </b-row>

                                <b-row class="mPageModalRow">
                                    <b-col sm="3" align-v="end">
                                        <label>Дата окончания</label>
                                    </b-col>
                                    <b-col sm="9">
                                        <template>
                                            <div>
                                                <b-form-datepicker id="task-end-datepicker" v-model="taskFinishDate"
                                                                   placeholder="Выберите дату" locale="ru"
                                                                   class="mb-2"></b-form-datepicker>
                                            </div>
                                        </template>

                                    </b-col>
                                </b-row>

                            </b-container>


                        </div>
                        <b-row>
                            <b-col lg="3">
                            </b-col>
                            <b-col>
                            </b-col>
                            <b-col lg="5">
                                <b-button class="task_sh_btn"
                                          @click="createNewTask"
                                          :disabled="disableNewTask"
                                          variant="success" size="sm">Создать
                                </b-button>
                                <b-button class="task_sh_btn" @click="this.closeNewTaskPopup"
                                          variant="danger" size="sm">Отмена
                                </b-button>
                            </b-col>

                        </b-row>
                    </b-modal>
                </div>


                <div>
                    <b-modal id="bv-modal-task-delete" size="lg" hide-footer :no-close-on-backdrop="true">
                        <template v-slot:modal-title>
                            Вы действительно хотите удалить задачу?
                        </template>
                        <div class="mPageModal">

                        </div>
                        <b-row>
                            <b-col lg="3">
                            </b-col>
                            <b-col>
                            </b-col>
                            <b-col lg="5">
                                <b-button class="task_sh_btn" @click="deleteTask"
                                          variant="success" size="sm">Да, удалить
                                </b-button>
                                <b-button class="task_sh_btn" @click="$bvModal.hide('bv-modal-task-delete')"
                                          variant="danger" size="sm">Отмена
                                </b-button>
                            </b-col>

                        </b-row>
                    </b-modal>
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
                                <b-col>
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
                                    <b-form-datalist id="taskExecutors-list" :options="executors">
                                    </b-form-datalist>
                                </b-col>

                            </b-row>
                            <b-row>
                                <b-col>
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
                                          @click="changeTaskDetails"
                                          variant="success"
                                          size="sm"
                                          :disabled="disableUpdateTask"
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
        name: 'AdminTasks',
        watch: {
            $route: {
                immediate: true,
                handler(to) {
                    document.title = to.meta.title || 'TELPTracker';
                }
            },
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },

            rows() {
                return this.postsTask.length
            },

            disableNewTask() {
                return this.taskTitle.length === 0 || this.taskDescription.length === 0 ||
                    this.taskExecutor.length === 0 || this.taskStartDate === 0 ||
                    this.taskFinishDate.length === 0
            },

            disableUpdateTask() {
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
                postsTask: [],
                messageTask: '',
                message: '',
                taskTitle: '',
                taskDescription: '',
                taskExecutor: '',
                employeeByFio: '',
                taskExecutors: [],
                taskStartDate: '',
                taskFinishDate: '',
                executors: [],
                executor: [],
                isDeletePopup: false,
                currentPage: 1,
                perPage: 5,

                checkboxSelected: false,

                disableState: true,
                disableUpdateState: true,

                rowSelected: '',
                isInfoPopupVisible: false,
                files: [],

                items: [],
                selectMode: 'single',

                messageView: false,
                boxOne: '',
                taskFields: [
                    {key: 'taskId', label: '#'},
                    {key: 'title', label: 'Название', sortable: true},
                    {key: 'startDate', label: 'Начало', sortable: true},
                    {key: 'finishDate', label: 'Окончание', sortable: true},
                    {key: 'status', label: 'Статус', sortable: true},
                    {key: 'members', label: "Исполнитель", sortable: true}
                ],
                busy: false,
                newTaskExecutor: '',
                newTaskFinishDate: '',
                currentFinishDate: '',
                currentTaskExecutor: '',
                currentTaskId: '',
                filter: null,
                totalRows: 1,
            }
        },
        methods: {

            getAllTasks() {
                this.busy = true;
                axios.get('/api/tasks/getall'
                ).then(response => {
                    console.log('success');
                    this.message = "Список задач загружен";
                    this.postsTask = response.data;
                    this.$bvToast.show('success-toast')
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось загрузить задачи!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            searchTaskByCriteria() {
                this.busy = true;
                let text = this.filter;
                axios.post('/api/tasks/searchall/' + text
                ).then(response => {
                    this.postsTask = response.data;
                    if (this.postsTask.length !== 0) {
                        console.log('success');
                        this.message = "Поиск завершен";
                        this.$bvToast.show('success-toast')
                    } else {
                        console.log('success');
                        this.message = "Искомая комбинация не найдена!";
                        this.$bvToast.show('success-toast')
                    }
                }).catch(error => {
                    console.log(error)
                    this.message = "Не удалось выполнить поиск!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },


            addNewTask() {
                this.$bvModal.show('bv-modal-task');
                this.getAllEmployees()

            },

            deleteCurrentTask() {
                this.$bvModal.show('bv-modal-task-delete')
            },

            getAllEmployees() {
                this.busy = true;
                axios.get('/api/employee/getall'
                ).then(response => {
                    console.log('success');
                    this.executors = response.data
                }).catch(error => {
                    console.log(error)
                }).finally(() => {
                    this.busy = false
                })
            },


            createNewTask() {
                let arr = this.taskExecutor.split(' ');
                axios.post('/api/employee/getbyfio',
                    arr,
                ).then(response => {
                    this.employeeByFio = response.data;
                    console.log('success');
                    this.saveNewTask()
                }).catch(error => {
                    console.log(error)
                }).finally(() => {
                    this.refreshNewTaskInputs()
                })
            },

            saveNewTask() {
                this.busy = true;
                let arr = [this.employeeByFio];
                axios.post('/api/tasks/admin/add',
                    {
                        title: this.taskTitle,
                        description: this.taskDescription,
                        startDate: this.taskStartDate,
                        finishDate: this.taskFinishDate,
                        members: arr
                    },
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Новая задача создана!";
                    this.$bvToast.show('success-toast');
                    this.getAllTasks();
                    this.$bvModal.hide('bv-modal-task')
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось создать задачу!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            closeNewTaskPopup() {
                this.$bvModal.hide('bv-modal-task');
                this.refreshNewTaskInputs()
            },

            refreshNewTaskInputs() {
                this.taskTitle = '';
                this.taskDescription = '';
                this.taskExecutor = '';
                this.taskStartDate = '';
                this.taskFinishDate = ''
            },


            deleteTask() {
                this.busy = true;
                let id = this.selected[0]["taskId"];
                axios.delete('/api/tasks/' + id + '/delete',
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Задача удалена.";
                    this.$bvToast.show('success-toast');
                    this.getAllTasks();
                    this.$bvModal.hide('bv-modal-task-delete')
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось удалить задачу!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            taskStart() {
                this.busy = true;
                let id = this.selected[0]["taskId"];
                axios.post('/api/tasks/status/' + id + '/start',
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Статус задачи изменен";
                    this.$bvToast.show('success-toast');
                    this.getAllTasks()
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось изменить статус!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            taskPause() {
                this.busy = true;
                let id = this.selected[0]["taskId"];
                axios.post('/api/tasks/status/' + id + '/pause',
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Статус задачи изменен";
                    this.$bvToast.show('success-toast');
                    this.getAllTasks()
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось изменить статус!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            taskEnd() {
                this.busy = true;
                let id = this.selected[0]["taskId"];
                axios.post('/api/tasks/status/' + id + '/end',
                ).then(response => {
                    console.log('success', response.data);
                    this.message = "Статус задачи изменен";
                    this.$bvToast.show('success-toast');
                    this.getAllTasks()
                }).catch(error => {
                    console.log(error);
                    this.message = "Не удалось изменить статус!";
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            taskChange() {
                this.currentTaskId = this.selected[0]["taskId"];
                this.currentFinishDate = this.selected[0]["finishDate"];
                this.currentTaskExecutor = this.selected[0]["members"][0];
                let exec = this.selected[0]["members"][0];
                for (let e in this.executors) {
                    if (this.executors[e].includes(exec)) {
                        this.taskExecutor = this.executors[e];
                        this.currentTaskExecutor = this.executors[e]
                    }
                }
                this.$bvModal.show('bv-modal-task-change')
            },

            changeTaskDetails() {
                if (this.newTaskExecutor.length !== 0) {
                    let arr = this.newTaskExecutor.split(' ');
                    axios.post('/api/employee/getbyfio',
                        arr,
                    ).then(response => {
                        this.employeeByFio = response.data;
                        console.log('success');
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
                if (this.employeeByFio.length !== 0) {
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
                        this.message = "Задача обновлена!";
                        this.$bvToast.show('success-toast');
                        this.$bvModal.hide('bv-modal-task-change')
                    }).catch(error => {
                        console.log(error);
                        this.message = "Не удалось обновить задачу!";
                        this.$bvToast.show('danger-toast')
                    }).finally(() => {
                        this.refreshUpdateTaskInputs();
                        this.getAllTasks();
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
                        this.message = "Задача обновлена!";
                        this.$bvToast.show('success-toast');
                        this.$bvModal.hide('bv-modal-task-change')
                    }).catch(error => {
                        console.log(error);
                        this.message = "Не удалось обновить задачу!";
                        this.$bvToast.show('danger-toast')
                    }).finally(() => {
                        this.refreshUpdateTaskInputs();
                        this.getAllTasks();
                        this.busy = false
                    })
                }
            },

            refreshUpdateTaskInputs() {
                this.newTaskExecutor = '';
                this.newTaskFinishDate = ''
            },

            onRowSelected(items) {
                this.selected = items;
                if (this.selected.length === 0) {
                    this.disableState = true
                } else {
                    this.disableState = false
                }
            },

            onRowDoubleClick(item) {
                this.$router.push({name: 'currentTask', params: {Pid: item.taskId}})
            },

            onFiltered(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                this.totalRows = filteredItems.length;
                this.currentPage = 1
            },

            clearSearchInput() {
                this.filter = '';
                this.getAllTasks()
            },

            enterSubmitSearch(event) {
                if (event.which === 13) {
                    this.searchTaskByCriteria()
                }
            }

        },

        beforeMount() {
            this.getAllTasks();
            this.getAllEmployees()

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

    .mPageModal {
        font-family: Arial;
        margin: 10px 10px 10px 10px;
        font-size: 18px;
    }

    .mPageModalRow {
        margin: 0 0 10px 0;
    }

    .custom_pagination {
        margin: 0 0 15px 0;
    }

    .task-update-sm-letter {
        font-family: Arial;
        margin: 2px 0 0 0;
        font-size: 16px;
        color: grey;
        text-align: center;
        font-weight: bold;
    }

    .task-update-st-letter {
        font-family: Arial;
        font-size: 16px;
        text-align: center;
        font-weight: bold;
    }

    .task_ls_btn {
        margin: 0 0 0 10px;
        text-align: center;
        width: 90px;
        font-family: Arial;
        font-size: 14px;
    }

    .task_lt_btn {
        margin: 0 0 0 5px;
        text-align: center;
        width: 90px;
        font-family: Arial;
        font-size: 14px;
    }

    .task_mid_btn {
        margin: 0 0 0 5px;
        text-align: center;
        width: 110px;
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

    .task_fl_btn {
        margin: 0 0 0 5px;
        text-align: center;
        width: 140px;
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

    .nav-link {
        color: black;
    }

</style>