import React, {Component} from 'react';
import PropTypes from "prop-types";
import InputMask from "react-input-mask";
import Cities from "./Cities";
import axios from "axios";
import Invalidities from "./Invalidities";
import FamilyStatuses from "./FamilyStatuses";
import Nationalities from "./Nationalities";
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';
import {Button, Container} from "@material-ui/core";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';


class RegisterUser extends Component{
    emptyItem = {
        id:'',
        lastname:'',
        firstname:'',
        surname: '',
        birthday: new Date(),
        city:'',
        series:'',
        passportNumber:'',
        issued:'',
        issueDate:new Date(),
        identifiedNumber:'',
        birthPlace:'',
        address:'',
        homePhone:'',
        email:'',
        mobilePhone:'',
        workPlace:'',
        position:'',
        registrationCity:'',
        familyStatus:'',
        nationality:'',
        invalidity:'',
        pensioner:'',
        income: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            lastnameDirty:false,
            lastnameError:'Поле фамилия не может быть пустым',
            firstnameDirty:false,
            firstnameError:'Поле имя не может быть пустым',
            surnameDirty:false,
            surnameError:'Поле отчество не может быть пустым',
            birthdayDirty:false,
            birthdayError:'Поле День рождение не может быть пустым',
            seriesDirty:false,
            seriesError:'Поле серия не может быть пустым',
            passportNumberDirty:false,
            passportNumberError:'Поле номер паспорта не может быть пустым',
            issuedDirty:false,
            issuedError:'Поле Кем выдан не может быть пустым',
            issueDateDirty:false,
            issueDateError:'Поле Дата выдачи не может быть пустым',
            identifiedNumberDirty:false,
            identifiedNumberError:'Поле номер паспорта не может быть пустым',
            birthPlaceDirty:false,
            birthPlaceError:'',
            addressDirty:false,
            addressError:'',
            homePhoneDirty:false,
            homePhoneError:'',
            emailDirty:false,
            emailError:'',
            mobilePhoneDirty:false,
            mobilePhoneError:'',
            cityDirty:false,
            cityError:'Поле Город факт. проживания не может быть пустым',
            registrationCityDirty:false,
            registrationCityError:'Поле Город прописки не может быть пустым',
            familyStatusDirty:false,
            familyStatusError:'Поле Семейное положение не может быть пустым',
            nationalityDirty:false,
            nationalityError:'Поле Гражданство не может быть пустым',
            invalidityDirty:false,
            invalidityError:'Поле Инвалидность не может быть пустым',
            cities:[],
            registrationCities:[],
            familyStatuses:[],
            nationalities:[],
            invalidities:[]
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            axios.get(`/users/${this.props.match.params.id}`)
                .then(response => {
                    this.setState({item: response.data,
                        lastnameError:'',
                        firstnameError:'',
                        surnameError:'',
                        birthdayError:'',
                        seriesError:'',
                        passportNumberError:'',
                        issuedError:'',
                        issueDateError:'',
                        identifiedNumberError:'',
                        birthPlaceError:'',
                        addressError:'',
                        homePhoneError:'',
                        emailError:'',
                        mobilePhoneError:'',
                        cityError:'',
                        registrationCityError:'',
                        familyStatusError:'',
                        nationalityError:'',
                        invalidityError:''});
                })
        }
        axios.get('/city/all')
            .then(response =>
            {
                this.setState({cities:response.data});
                this.setState({registrationCities:response.data});
            })
        axios.get('/familyStatus/all')
            .then(response =>
            {
                this.setState({familyStatuses:response.data});
            })
        axios.get('/invalidity/all')
            .then(response =>
            {
                this.setState({invalidities:response.data});
            })

        axios.get('/nationality/all')
            .then(response =>
            {
                this.setState({nationalities:response.data});
            })
    }

    onSubmit(e){
        e.preventDefault();
        if(this.state.item.id)
        {
            axios.put('/users/' + this.state.item.id, this.state.item)
                .then(r  => {
                    alert("Информация о клиенте обновлена")
                    this.props.history.push('/')
                })
                .catch(r =>
                {
                    alert("Ошибка во врем добавления")

                })

        }
        else
        {
            axios.post('/users', this.state.item)
                .then(r  => {

                    alert("Клиент успешно создан")
                    this.props.history.push('/')
                })
                .catch(r =>
                {
                    alert("Ошибка во врем создания")
                })
        }


    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    lastnameHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле фамилия не может быть пустым"
        }

        if(!error && e.target.value.length < 2){
            error = "Длина фамилии должна быть больше или равно 2 символам"
        }

        if(!error && /[^a-zA-Zа-яА-Я]/.test((e.target.value)))
        {
            error = "Фамилия должна сожержать только буквы"
        }

        this.setState({lastnameError: error})
    }

    firstnameHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле имя не может быть пустым"
        }

        if(!error && e.target.value.length < 2){
            error = "Длина имя должна быть больше или равно 2 символам"
        }

        if(!error && /[^a-zA-Zа-яА-Я]/.test((e.target.value)))
        {
            error = "Имя должно сожержать только буквы"
        }

        this.setState({firstnameError: error})
    }

    surnameHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле отчество не может быть пустым"
        }

        if(!error && /[^a-zA-Zа-яА-Я]/.test((e.target.value)))
        {
            error = "Отчество должно сожержать только буквы"
        }

        this.setState({surnameError: error})
    }

    birthdayHandler =(e)=>{
        this.handleChange(e)
        let error = ''
        if( new Date() < new Date(e.target.value) && new Date(new Date() - new Date(e.target.value)).toISOString().slice(0, 4) - 1970 < 18){
            error = "Клиент должн быть совершеннолетним"
        }

        this.setState({birthdayError: error})
    }

    seriesHandler  =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле серия не может быть пустым"
        }

        if(!error && e.target.value.length !== 2){
            error = "Длина поля серия должна содержать ровно 2 символа"
        }

        if(!error && /[^A-ZА-Я]/.test((e.target.value)))
        {
            error = "Отчество должно сожержать только заглавные буквы"
        }

        this.setState({seriesError: error})
    }

    passportNumberHandler  =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле номер паспорта не может быть пустым"
        }

        if(!error && e.target.value.length !== 7){
            error = "Длина номера паспорта должна содержать ровно 7 символов"
        }

        if(!error && /[^0-9]/.test((e.target.value)))
        {
            error = "Номер паспорта должен сожержать только цифры"
        }

        this.setState({passportNumberError: error})
    }

    issuedHandler  =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Кем выдан не может быть пустым"
        }

        this.setState({issuedError: error})
    }

    issueDateHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(new Date() < new Date(e.target.value)){
            error = "Дата выдачи не может быть больше, чем сегодня"
        }

        if(!error && new Date(new Date() - new Date(e.target.value)).toISOString().slice(0, 4) - 1970 > 10){
            error = "Время с даты выдачи паспорта не должно превышать 10 лет"
        }

        this.setState({issueDateError: error})
    }

    identifiedNumberHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Идентификационный номер не может быть пустым"
        }

        if(!error && e.target.value.length !== 14){
            error = "Длина идентификационного номера должна быть 14 символов"
        }

        if(!error && /^[0-9]{7}[A-Z][0-9]{3}[A-Z]{2}[0-9]$/.test((e.target.value)))
        {
            error = "Не корректный формат идентификационного номера "
        }

        this.setState({identifiedNumberError: error})
    }

    birthPlaceHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Место рождения не может быть пустым"
        }

        this.setState({birthPlaceError: error})
    }

    addressHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Адрес факт.проживания не может быть пустым"
        }

        this.setState({addressError: error})
    }

    homePhoneHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value !== '' && !/^[0-9]{2}-[0-9]{2}-[0-9]{2}$/.test((e.target.value)))
        {
            error = "Не корректный формат поля Телефон дом"
        }

        this.setState({homePhoneError: error})
    }

    mobilePhoneHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value !== '+375(' && !/^\+375\([0-9]{2}\)-[0-9]{3}-[0-9]{2}-[0-9]{2}$/.test(e.target.value))
        {
            error = "Не корректный формат Телефон моб"
        }

        this.setState({mobilePhoneError: error})
    }

    emailHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value !== '' && !/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(e.target.value))
        {
            error = "Не корректный формат Email"
        }

        this.setState({emailError: error})
    }

    cityHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Город факт. проживания не может быть пустым"
        }

        this.setState({cityError: error})
    }

    registrationCityHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Город прописки не может быть пустым"
        }

        this.setState({registrationCityError: error})
    }

    familyStatusHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Семейное положение не может быть пустым"
        }

        this.setState({familyStatusError: error})
    }

    nationalityHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Гражданство не может быть пустым"
        }

        this.setState({nationalityError: error})
    }

    invalidityHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Инвалидность не может быть пустым"
        }

        this.setState({invalidityError: error})
    }

    blurHandler =(e) => {
        if(e.target.name === 'lastname')
        {
            this.setState({lastnameDirty: true})
            if(this.state.lastnameError){
                NotificationManager.error(this.state.lastnameError)
            }
        }
        else
        {
            this.setState({lastnameDirty: false})
        }

        if(e.target.name === 'firstname')
        {
            this.setState({firstnameDirty: true})
            if(this.state.firstnameError){
                NotificationManager.error(this.state.firstnameError)
            }
        }
        else
        {
            this.setState({firstnameDirty: false})
        }

        if(e.target.name === 'surname')
        {
            this.setState({surnameDirty: true})
            if(this.state.surnameError){
                NotificationManager.error(this.state.surnameError)
            }
        }
        else
        {
            this.setState({surnameDirty: false})
        }

        if(e.target.name === 'birthday')
        {
            this.setState({birthdayDirty: true})
            if(this.state.birthdayError){
                NotificationManager.error(this.state.birthdayError)
            }
        }
        else
        {
            this.setState({birthdayDirty: false})
        }

        if(e.target.name === 'series')
        {
            this.setState({seriesDirty: true})
            if(this.state.seriesError){
                NotificationManager.error(this.state.seriesError)
            }
        }
        else
        {
            this.setState({seriesDirty: false})
        }

        if(e.target.name === 'passportNumber')
        {
            this.setState({passportNumberDirty: true})
            if(this.state.passportNumberError){
                NotificationManager.error(this.state.passportNumberError)
            }
        }
        else
        {
            this.setState({passportNumberDirty: false})
        }

        if(e.target.name === 'issued')
        {
            this.setState({issuedDirty: true})
            if(this.state.issuedError){
                NotificationManager.error(this.state.issuedError)
            }
        }
        else
        {
            this.setState({issuedDirty: false})
        }

        if(e.target.name === 'issueDate')
        {
            this.setState({issueDateDirty: true})
            if(this.state.issueDateError){
                NotificationManager.error(this.state.issueDateError)
            }
        }
        else
        {
            this.setState({issueDateDirty: false})
        }

        if(e.target.name === 'identifiedNumber')
        {
            this.setState({identifiedNumberDirty: true})
            if(this.state.identifiedNumberError){
                NotificationManager.error(this.state.identifiedNumberError)
            }
        }
        else
        {
            this.setState({identifiedNumberDirty: false})
        }

        if(e.target.name === 'birthPlace')
        {
            this.setState({birthPlaceDirty: true})
            if(this.state.birthPlaceError){
                NotificationManager.error(this.state.birthPlaceError)
            }
        }
        else
        {
            this.setState({birthPlaceDirty: false})
        }

        if(e.target.name === 'address')
        {
            this.setState({addressDirty: true})
            if(this.state.addressError){
                NotificationManager.error(this.state.addressError)
            }
        }
        else
        {
            this.setState({addressDirty: false})
        }

        if(e.target.name === 'homePhone')
        {
            this.setState({homePhoneDirty: true})
            if(this.state.homePhoneError){
                NotificationManager.error(this.state.homePhoneError)
            }
        }
        else
        {
            this.setState({homePhoneDirty: false})
        }

        if(e.target.name === 'email')
        {
            this.setState({emailDirty: true})
            if(this.state.emailError){
                NotificationManager.error(this.state.emailError)
            }
        }
        else
        {
            this.setState({emailDirty: false})
        }

        if(e.target.name === 'mobilePhone')
        {
            this.setState({mobilePhoneDirty: true})
            if(this.state.mobilePhoneError){
                NotificationManager.error(this.state.mobilePhoneError)
            }
        }
        else
        {
            this.setState({mobilePhoneDirty: false})
        }

        if(e.target.name === 'city')
        {
            this.setState({cityDirty: true})
            if(this.state.cityError){
                NotificationManager.error(this.state.cityError)
            }
        }
        else
        {
            this.setState({cityDirty: false})
        }

        if(e.target.name === 'registrationCity')
        {
            this.setState({registrationCityDirty: true})
            if(this.state.registrationCityError){
                NotificationManager.error(this.state.registrationCityError)
            }
        }
        else
        {
            this.setState({registrationCityDirty: false})
        }

        if(e.target.name === 'familyStatus')
        {
            this.setState({familyStatusDirty: true})
            if(this.state.familyStatusError){
                NotificationManager.error(this.state.familyStatusError)
            }
        }
        else
        {
            this.setState({familyStatusDirty: false})
        }

        if(e.target.name === 'nationality')
        {
            this.setState({nationalityDirty: true})
            if(this.state.nationalityError){
                NotificationManager.error(this.state.nationalityError)
            }
        }
        else
        {
            this.setState({nationalityDirty: false})
        }

        if(e.target.name === 'invalidity')
        {
            this.setState({invalidityDirty: true})
            if(this.state.invalidityError){
                NotificationManager.error(this.state.invalidityError)
            }
        }
        else
        {
            this.setState({invalidityDirty: false})
        }
    }

    render() {
        const title = <h2>{this.state.item.id ? 'Edit Client' : 'Add Client'}</h2>;

        return(
            <div>
                <AppNavbar/>
                <Container>
                    {title}
                    <form onSubmit={this.onSubmit.bind(this)}>
                        <div >
                            <div style={label_left}>

                                <label form="lastname">Фамилия:</label>
                                <br/>
                                {}
                                <input
                                    type = "text"
                                    id = "lastname"
                                    style={leftInput}
                                    name = "lastname"
                                    required
                                    value={this.state.item.lastname}
                                    onChange={this.lastnameHandler}
                                    onBlur={this.blurHandler}
                                />
                                <NotificationContainer/>
                            </div>

                            <div style={label_right}>
                                <label form="firstname" >Имя:</label>
                                <br/>
                                <input
                                    type = "text"
                                    id = "firstname"
                                    name = "firstname"
                                    required
                                    style={rightInput}
                                    value={this.state.item.firstname}
                                    onChange={this.firstnameHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <br/>

                            <div style={label_left}>
                                <label form="surname">Отчество:</label>
                                <br/>
                                <input
                                    type = "text"
                                    id = "surname"
                                    name = "surname"
                                    required
                                    style={leftInput}
                                    value={this.state.item.surname}
                                    onChange={this.surnameHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <div style={label_right}>
                                <label form="birthday" >Дата рождения:</label>
                                <br/>
                                <input
                                    type = "date"
                                    id = "birthday"
                                    name = "birthday"
                                    required
                                    style={rightInput}
                                    value={this.state.item.birthday}
                                    onChange={this.birthdayHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>
                            <br/>
                            <div style={label_left}>
                                <label form="series">Серия паспорта:</label>
                                <br/>
                                <input
                                    type = "text"
                                    id = "series"
                                    name = "series"
                                    required
                                    maxLength={2}
                                    minLength={2}
                                    style={leftInput}
                                    value={this.state.item.series}
                                    onChange={this.seriesHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <div style={label_right}>
                                <label form="passportNumber" >№ паспорта:</label>
                                <br/>
                                <InputMask
                                    type = "text"
                                    name = "passportNumber"
                                    id="passportNumber"
                                    required
                                    mask="9999999"
                                    maskChar=""
                                    alwaysShowMask
                                    style={rightInput}
                                    value={this.state.item.passportNumber}
                                    onChange={this.passportNumberHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="issued">Кем выдан:</label>
                                <br/>
                                <input
                                    type = "text"
                                    name = "issued"
                                    id = "issued"
                                    required
                                    style={leftInput}
                                    value={this.state.item.issued}
                                    onChange={this.issuedHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <div style={label_right}>
                                <label form="issueDate">Дата выдачи:</label>
                                <br/>
                                <input
                                    type = "date"
                                    name = "issueDate"
                                    id="issueDate"
                                    required
                                    style={rightInput}
                                    value={this.state.item.issueDate}
                                    onChange={this.issueDateHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="identifiedNumber">Идент. номер:</label>
                                <br/>
                                <InputMask
                                    type = "text"
                                    required
                                    name = "identifiedNumber"
                                    id="identifiedNumber"
                                    mask="9999999a999aa9"
                                    maskChar={null}
                                    alwaysShowMask
                                    style={leftInput}
                                    value={this.state.item.identifiedNumber}
                                    onChange={this.identifiedNumberHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <div style={label_right}>
                                <label form="birthPlace" >Место рождения:</label>
                                <br/>
                                <input
                                    type = "text"
                                    name = "birthPlace"
                                    id="birthPlace"
                                    required
                                    style={rightInput}
                                    value={this.state.item.birthPlace}
                                    onChange={this.birthPlaceHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>
                            <br/>
                            <div style={label_left}>
                                <label form="cities">Город факт. проживани:</label>
                                <br/>
                                <select style={leftInput} name="city" onBlur={this.blurHandler} onChange={this.cityHandler.bind(this)}>
                                    <option></option>
                                    <Cities cities={this.state.cities} city={this.state.item.city}/>
                                </select>
                            </div>

                            <div style={label_right}>
                                <label form="address" >Адрес факт.проживания:</label>
                                <br/>
                                <input
                                    type = "text"
                                    name = "address"
                                    id="address"
                                    required
                                    style={rightInput}
                                    value={this.state.item.address}
                                    onChange={this.addressHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="homePhone">Телефон дом:</label>
                                <br/>
                                <InputMask
                                    type = "tel"
                                    name = "homePhone"
                                    id="homePhone"
                                    mask="99-99-99"
                                    maskChar=""
                                    alwaysShowMask
                                    style={leftInput}
                                    value={this.state.item.homePhone}
                                    onChange={this.homePhoneHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <div style={label_right}>
                                <label form="mobilePhone">Телефон моб:</label>
                                <br/>
                                <InputMask
                                    type = "tel"
                                    name = "mobilePhone"
                                    mask="+375(99)-999-99-99"
                                    maskChar=""
                                    alwaysShowMask
                                    id="mobilePhone"
                                    style={rightInput}
                                    value={this.state.item.mobilePhone}
                                    onChange={this.mobilePhoneHandler}
                                    onBlur={this.blurHandler}
                                />

                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="email">E-mail:</label>
                                <br/>
                                <input
                                    type = "text"
                                    name = "email"
                                    id="email"
                                    style={leftInput}
                                    value={this.state.item.email}
                                    onChange={this.emailHandler}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <div style={label_right}>
                                <label form="workPlace">Место работы:</label>
                                <br/>
                                <input
                                    type = "text"
                                    name = "workPlace"
                                    id="workPlace"
                                    style={rightInput}
                                    value={this.state.item.workPlace}
                                    onChange={this.handleChange.bind(this)}
                                    onBlur={this.blurHandler}
                                />
                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="position">Должность:</label>
                                <br/>
                                <input
                                    type = "text"
                                    name = "position"
                                    id="position"
                                    style={leftInput}
                                    value={this.state.item.position}
                                    onChange={this.handleChange.bind(this)}
                                    onBlur={this.blurHandler}
                                />
                            </div>
                            <div style={label_right}>
                                <label form="registrationCities" >Город прописки:</label>
                                <br/>
                                <select style={rightInput} name = "registrationCity" onBlur={this.blurHandler}  onChange={this.registrationCityHandler.bind(this)}>
                                    <option></option>
                                    <Cities cities={this.state.registrationCities} city = {this.state.item.registrationCity}/>
                                </select>
                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="familyStatus" >Семейное положение:</label>
                                <br/>
                                <select style={leftInput} name = "familyStatus" onBlur={this.blurHandler} onChange={this.familyStatusHandler.bind(this)}>
                                    <option></option>
                                    <FamilyStatuses familyStatuses={this.state.familyStatuses} familyStatus={this.state.item.familyStatus}/>
                                </select>
                            </div>

                            <div style={label_right}>
                                <label form="nationality" >Гражданство:</label>
                                <br/>
                                <select style={rightInput} name = "nationality" onBlur={this.blurHandler} onChange={this.nationalityHandler.bind(this)}>
                                    <option></option>
                                    <Nationalities nationalities={this.state.nationalities} nationality={this.state.item.nationality}/>
                                </select>
                            </div>


                            <br/>

                            <div style={label_left}>
                                <label form="invalidity" >Инвалидность:</label>
                                <br/>
                                <select style={leftInput} name = "invalidity" onBlur={this.blurHandler} onChange={this.invalidityHandler.bind(this)}>
                                    <option></option>
                                    <Invalidities invalidities={this.state.invalidities} invalidity={this.state.item.invalidity}/>
                                </select>
                            </div>

                            <div style={label_right}>
                                <label form="pensioner" >
                                    <input
                                        type = "checkbox"
                                        name = "pensioner"
                                        id="pensioner"
                                        style={checkbox}
                                        value={this.state.item.pensioner}
                                        onChange={this.handleChange.bind(this)}
                                        onBlur={this.blurHandler}
                                    />Пенсионер</label>
                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="income">Ежемесячный доход:</label>
                                <br/>
                                <input
                                    type = "number"
                                    name = "income"
                                    id="income"
                                    style={leftInput}
                                    value={this.state.item.income}
                                    onChange={this.handleChange.bind(this)}
                                    onBlur={this.blurHandler}
                                />
                            </div>
                        </div>
                        <br/>
                        <Button color="primary" type="submit" disabled=
                                {  this.state.lastnameError
                                || this.state.firstnameError
                                || this.state.surnameError
                                || this.state.birthdayError
                                || this.state.seriesError
                                || this.state.passportNumberError
                                || this.state.issuedError
                                || this.state.issueDateError
                                || this.state.identifiedNumberError
                                || this.state.birthPlaceError
                                || this.state.addressError
                                || this.state.homePhoneError
                                || this.state.emailError
                                || this.state.mobilePhoneError
                                || this.state.cityError
                                || this.state.registrationCityError
                                || this.state.familyStatusError
                                || this.state.nationalityError
                                || this.state.invalidityError
                            }>Save</Button>{' '}
                        <Button color="secondary"><Link to="/">Cancel</Link></Button>
                        <br/>
                    </form>
                </Container>
            </div>
        )
    }
}

const checkbox ={
    margin:'30px 10px 30px 0px',
}
const submit = {
    width: '10%',
    margin: '20px 0 20px 45%'
}

const leftInput = {
    flex:'5',
    padding:'5px',
    margin:'10px 0px 0px 0px',
    width:'80%'
}
const label_right = {
    float: 'right',
    width:'55%',
    marginTop: '10px',
    paddingLeft: '5%'
}
const label_left = {
    float: 'left',
    width:'45%',
    marginTop: '10px',
    paddingLeft: '5%'
}
const rightInput = {
    flex:'5',
    padding:'5px',
    margin:'10px 0px 0px 0px',
    width:'80%'
}


export default withRouter(RegisterUser);