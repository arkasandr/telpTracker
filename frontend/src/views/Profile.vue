<template>
    <div class="container">
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
                    <b-col>Профиль сотрудника</b-col>
                    <b-col></b-col>
                </b-row>
                <p>

                </p>

                <b-row class="mPageText">
                    <b-col><strong> Фамилия:</strong> {{this.employee.surname}}</b-col>
                </b-row>
                <b-row class="mPageText">
                    <b-col><strong>Имя: </strong> {{this.employee.firstName}}</b-col>
                </b-row>
                <b-row class="mPageText">
                    <b-col><strong>Отчество:</strong> {{this.employee.middleName}}</b-col>
                </b-row>
                <b-row class="mPageText">
                    <b-col><strong>Отдел:</strong> {{this.employee.department}}</b-col>
                </b-row>
                <b-row class="mPageText">
                    <b-col><strong>Группа:</strong> {{this.employee.groupNumber}}</b-col>
                </b-row>
                <b-row class="mPageText">
                    <b-col><strong>Должность:</strong> {{this.employee.position}}</b-col>
                </b-row>
                <b-row class="mPageText">
                    <b-col><strong>Электронная почта:</strong> {{this.employee.email}}</b-col>
                </b-row>
                <b-row>
                    <b-col>
                    </b-col>
                    <b-col>
                    </b-col>
                    <b-col>
                    </b-col>
                    <b-col>
                        <b-button class="ls_btn" @click="changeEmployeeInfo"
                                  variant="success" size="lg">
                            Изменить профиль
                        </b-button>
                    </b-col>
                </b-row>


            </b-jumbotron>

            <div>
                <b-modal id="bv-modal-info" size="lg" hide-footer :no-close-on-backdrop="true">
                    <template v-slot:modal-title>
                        Изменить данные профиля
                    </template>
                    <div class="mPageModal">
                        <b-container>

                            <b-row class="mPageModalRow">
                                <b-col sm="3" align-v="end">
                                    <label>Фамилия</label>
                                </b-col>
                                <b-col sm="9">
                                    <b-form-input
                                            id="input-live-surname"
                                            v-model="newSurname"
                                            aria-describedby="input-live-surname-help input-live-surname-feedback"
                                            placeholder="Введите фамилию"
                                            trim
                                    ></b-form-input>

                                </b-col>
                            </b-row>

                            <b-row class="mPageModalRow">
                                <b-col sm="3" align-v="end">
                                    <label>Имя</label>
                                </b-col>
                                <b-col sm="9">
                                    <b-form-input
                                            id="input-live-firstname"
                                            v-model="newFirstname"
                                            aria-describedby="input-live-firstname-help input-live-firstname-feedback"
                                            placeholder="Введите имя"
                                            trim
                                    ></b-form-input>
                                </b-col>
                            </b-row>

                            <b-row class="mPageModalRow">
                                <b-col sm="3" align-v="end">
                                    <label>Отчество</label>
                                </b-col>
                                <b-col sm="9">
                                    <b-form-input
                                            id="input-live-middlename"
                                            v-model="newMiddlename"
                                            aria-describedby="input-live-middlename-help input-live-middlename-feedback"
                                            placeholder="Введите отчество"
                                            trim
                                    ></b-form-input>
                                </b-col>
                            </b-row>

                            <b-row class="mPageModalRow">
                                <b-col sm="3" align-v="end">
                                    <label>Отдел</label>
                                </b-col>
                                <b-col sm="9">
                                    <b-form-input
                                            id="department"
                                            v-model="newDepartment"
                                            aria-describedby="input-live-department-help input-live-department-feedback"
                                            placeholder="Выберите отдел из списка"
                                            list="department-list"
                                    ></b-form-input>
                                    <b-form-datalist id="department-list" :options="departments">
                                    </b-form-datalist>

                                </b-col>
                            </b-row>

                            <b-row class="mPageModalRow">
                                <b-col sm="3" align-v="end">
                                    <label>Группа</label>
                                </b-col>
                                <b-col sm="9">
                                    <b-form-input
                                            id="groupnumber"
                                            v-model="newGroupnumber"
                                            aria-describedby="input-live-groupnumber-help input-live-groupnumber-feedback"
                                            placeholder="Выберите группу из списка"
                                            list="groupnumber-list"
                                    ></b-form-input>
                                    <b-form-datalist id="groupnumber-list" :options="groupnumbers">
                                    </b-form-datalist>

                                </b-col>
                            </b-row>

                            <b-row class="mPageModalRow">
                                <b-col sm="3" align-v="end">
                                    <label>Должность</label>
                                </b-col>
                                <b-col sm="9">
                                    <b-form-input
                                            id="position"
                                            v-model="newPosition"
                                            aria-describedby="input-live-position-help input-live-position-feedback"
                                            placeholder="Выберите должность из списка"
                                            list="position-list"
                                    ></b-form-input>
                                    <b-form-datalist id="position-list" :options="positions">
                                    </b-form-datalist>

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
                            <b-button class="sh_btn" @click="saveEmployeeInfo"
                                      variant="outline-dark" size="sm">Сохранить
                            </b-button>
                            <b-button class="sh_btn" @click="$bvModal.hide('bv-modal-info')"
                                      variant="outline-dark" size="sm">Отмена
                            </b-button>
                        </b-col>

                    </b-row>
                </b-modal>
            </div>


            <b-toast id="success-toast" variant="success" solid :append-toast=true>
                <template v-slot:toast-title>
                    <div class="d-flex flex-grow-1 align-items-baseline">
                        <b-img blank blank-color="#8FBC8F" class="mr-2" width="12" height="12"></b-img>
                        <strong class="mr-auto">Успешно!</strong>
                    </div>
                </template>
                {{ this.message }}
            </b-toast>

            <b-toast id="warning-toast" variant="warning" solid :append-toast=true>
                <template v-slot:toast-title>
                    <div class="d-flex flex-grow-1 align-items-baseline">
                        <b-img blank blank-color="#FF8C00" class="mr-2" width="12" height="12"></b-img>
                        <strong class="mr-auto">Предупреждение!</strong>
                    </div>
                </template>
                {{ this.message }}
            </b-toast>

            <b-toast id="danger-toast" variant="danger" solid :append-toast=true>
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
        name: 'Profile',
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            }
        },
        mounted() {
            if (!this.currentUser) {
                this.$router.push('/login');
            }
        },
        data() {
            return {
                employee: '',
                departments: [
                    'Электроснабжения',
                    'Контактной сети', 'СЦБ и связи', 'Общестроительный', 'Тяговых подстанций'
                ],
                groupnumbers: [1, 2, 3, 4],
                positions: [
                    'инженер',
                    'инженер 2 категории', 'инженер 1 категории', 'ведущий инженер'
                ],
                newSurname: '',
                newFirstname: '',
                newMiddlename: '',
                newDepartment: '',
                newPosition: '',
                newGroupnumber: '',


                currentPage: 1,
                checkboxSelected: false,
                isBusyTable: false,
                disableState: true,
                disableStateDownloadOriginArchive: true,
                disableStateDownloadModifyArchive: true,
                disableStateCheck: true,
                disableStateErratumXsd: true,
                disableStateErratumXsdCheck: true,
                disableSaveAllErratumsToArchive: true,
                rowSelected: '',
                isInfoPopupVisible: false,
                files: [],

                items: [],
                selectMode: 'single',
                message: '',
                messageLast: '',
                posts: [],
                messageView: false,
                boxOne: '',
                fieldsXsd: [
                    {key: 'attribute', label: 'Атрибут', sortable: true},
                    {key: 'wrongValue', label: 'Ошибочное значение '},
                    {key: 'correctValue', label: 'Корректное значение'},
                    {key: 'action', label: 'Действие'},
                ],
                postsXsd: [],
                fieldsErratum: [
                    {key: 'wrongValue', label: 'Ошибочное значение '},
                    {key: 'correctValue', label: 'Корректное значение'},
                ],
                postsErratum: [],
                archiveId: [],
                busy: false,
                erratumCount: 0
            }
        },
        methods: {

            getEmployeeInfo() {
                this.busy = true
                axios.get('/api/employee/info'
                ).then(response => {
                    if (response.data.length !== 0) {
                        console.log('success', response.data)
                        this.message = "Профиль загружен"
                        this.employee = response.data
                        this.$bvToast.show('success-toast')
                    } else {
                        console.log('success', response.data)
                        this.message = "Заполните профиль"
                        this.employee = response.data
                        this.$bvToast.show('success-toast')
                    }
                }).catch(error => {
                    console.log(error)
                    this.message = "Не удалось загрузить профиль!"
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.busy = false
                })
            },

            changeEmployeeInfo() {
                this.$bvModal.show('bv-modal-info')
            },

            saveEmployeeInfo() {
                this.busy = true
                let currentObj = this;

                axios.post('/api/employee/changeinfo',
                    {
                        surname: this.newSurname,
                        firstName: this.newFirstname,
                        middleName: this.newMiddlename,
                        department: this.newDepartment,
                        groupNumber: this.newGroupnumber,
                        position: this.newPosition
                    },
                ).then(response => {
                    currentObj.output = response.data;
                    console.log('success', response.data)
                    this.message = "Профиль успешно обновлен"
                    this.$bvToast.show('success-toast')
                }).catch(error => {

                    console.log(error)
                    this.message = "Не удалось обновить профиль!"
                    this.$bvToast.show('danger-toast')
                }).finally(() => {
                    this.$bvModal.hide('bv-modal-info')
                    this.busy = false
                    this.getEmployeeInfo()
                })
            },

        },

        beforeMount() {
            this.getEmployeeInfo()
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

    .mPageText {
        font-family: Arial;
        margin: 10px 10px 20px 10px;
        font-size: 18px;
    }

    .mPageModal {
        font-family: Arial;
        margin: 10px 10px 10px 10px;
        font-size: 18px;
    }

    .mPageModalRow {
        margin: 0 0 10px 0;
    }

    .ls_btn {
        margin: 10px 0 0 30px;
        text-align: center;
        width: 180px;
        font-family: Arial;
        font-size: 14px;
    }

    .sh_btn {
        margin: 10px 0 0 30px;
        text-align: center;
        width: 100px;
        font-family: Arial;
        font-size: 14px;
    }
</style>