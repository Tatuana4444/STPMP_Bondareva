import React, {Component} from 'react';
import axios from "axios";
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';
import {Button, Container} from "@material-ui/core";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import ContractTypes from "./ContractTypes";
import Users from "./Users";
import Currencies from "./Currencies";


class CreateContract extends Component{
    emptyContract = {
        id:'',
        startDate: '',
        endDate: '',
        contractType:'',
        user:'',
        currency:'',
        sum:'',
        percent:'',
        currentAccount: '',
        persentAccount: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            today: '',
            contract: this.emptyContract,
            startDateDirty:false,
            startDateError:'Поле Дата начала не может быть пустым',
            endDateDirty:false,
            endDateError:'Поле Дата окончания не может быть пустым',
            contractTypeDirty:false,
            contractTypeError:'Поле Вид контракта не может быть пустым',
            userDirty:false,
            userError:'Поле Клиент не может быть пустым',
            currencyDirty:false,
            currencyError:'Поле Вид валюты не может быть пустым',
            sumDirty:false,
            sumError:'Поле Сумма вклада не может быть пустым',
            percentDirty:false,
            percentError:'Поле Проценты не может быть пустым',
            contractTypes:[],
            users:[],
            currencies:[]
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        axios.get('/financialOperation')
            .then(response => {
                this.setState({today: response.data});
            })

        if (this.props.match.params.id !== 'new') {
            axios.get(`/contracts/${this.props.match.params.id}`)
                .then(response => {
                    console.log(response.data)
                    this.setState({
                        contract: response.data,
                        startDateError:'',
                        endDateError:'',
                        contractTypeError:'',
                        userError:'',
                        currencyError:'',
                        sumError:'',
                        percentError:''});
                })
        }
        axios.get('/contractType')
            .then(response =>
            {
                this.setState({contractTypes:response.data});
            })
        axios.get('/users')
            .then(response =>
            {
                this.setState({users:response.data});
            })
        axios.get('/currency')
            .then(response =>
            {
                this.setState({currencies:response.data});
            })
    }

    onSubmit(e){
        e.preventDefault();
        if(this.state.contract.id)
        {
            axios.put('/contracts/' + this.state.contract.id, this.state.contract)
                .then(_  => {
                    alert("Информация о контракте обновлена")
                    this.props.history.push('/agreements/')
                })
                .catch(_ =>
                {
                    alert("Ошибка во врем добавления")

                })

        }
        else
        {
            axios.post('/contracts', this.state.contract)
                .then(_  => {
                    alert("Контракт успешно создан")
                    this.props.history.push('/agreements/')
                })
                .catch(_ =>
                {
                    alert("Ошибка во врем создания")
                })
        }


    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let contract = {...this.state.contract};
        contract[name] = value;
        this.setState({contract});
    }

    startDateHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Дата начала не может быть пустым"
        }

        if( new Date(this.state.today) > new Date(e.target.value)){
            error = "Дата начала не может быть меньше текущей даты"
        }

        if(this.state.contract.endDate){
            if( new Date(this.state.contract.endDate) < new Date(e.target.value)){
                error = "Дата начала не может быть больше даты окончания"
            }
        }

        this.setState({startDateError: error})
    }

    endDateHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Дата окончания не может быть пустым"
        }

        if( new Date(this.state.today) > new Date(e.target.value)){
            error = "Дата окончания не может быть меньше текущей даты"
        }

        if(this.state.contract.endDate){
            if( new Date(this.state.contract.startDate) > new Date(e.target.value)){
                error = "Дата начала не может быть больше даты окончания"
            }
        }

        this.setState({endDateError: error})
    }

    contractTypeHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Вид контракта не может быть пустым"
        }

        this.setState({contractTypeError: error})
    }

    userHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Клиент не может быть пустым"
        }

        this.setState({userError: error})
    }

    currencyHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Вид валюты не может быть пустым"
        }

        this.setState({currencyError: error})
    }

    sumHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Сумма вклада не может быть пустым"
        }

        console.log(e.target.value)
        if(Number(e.target.value) <= 100){
            error = "Сумма должна быть больше или равнв 100"
        }

        this.setState({sumError: error})
    }

    percentHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value === ''){
            error = "Поле Проценнты не может быть пустым"
        }

        if(Number(e.target.value) > 30){
            error = "Проценты не должны быть больше 30"
        }

        this.setState({percentError: error})
    }

    blurHandler =(e) => {
console.log(this.state)
        if(e.target.name === 'startDate')
        {
            this.setState({startDateDirty: true})
            if(this.state.startDateError){
                NotificationManager.error(this.state.startDateError)
            }
        }
        else
        {
            this.setState({startDateDirty: false})
        }

        if(e.target.name === 'endDate')
        {
            this.setState({endDateDirty: true})
            if(this.state.endDateError){
                NotificationManager.error(this.state.endDateError)
            }
        }
        else
        {
            this.setState({endDateDirty: false})
        }

        if(e.target.name === 'contractType')
        {
            this.setState({contractTypeDirty: true})
            if(this.state.contractTypeError){
                NotificationManager.error(this.state.contractTypeError)
            }
        }
        else
        {
            this.setState({contractTypeDirty: false})
        }

        if(e.target.name === 'user')
        {
            this.setState({userDirty: true})
            if(this.state.userError){
                NotificationManager.error(this.state.userError)
            }
        }
        else
        {
            this.setState({userDirty: false})
        }

        if(e.target.name === 'currency')
        {
            this.setState({currencyDirty: true})
            if(this.state.currencyError){
                NotificationManager.error(this.state.currencyError)
            }
        }
        else
        {
            this.setState({currencyDirty: false})
        }

        if(e.target.name === 'Sum')
        {
            this.setState({SumDirty: true})
            if(this.state.sumError){
                NotificationManager.error(this.state.sumError)
            }
        }
        else
        {
            this.setState({SumDirty: false})
        }

        if(e.target.name === 'percent')
        {
            this.setState({percentDirty: true})
            if(this.state.percentError){
                NotificationManager.error(this.state.percentError)
            }
        }
        else
        {
            this.setState({percentDirty: false})
        }
    }

    render() {
        const title = <h2>{this.state.contract.id ? 'Редактирование контракта' : 'Создание контракта'}</h2>;

        return(
            <div>
                <AppNavbar/>
                <Container>
                    {title}
                    <form onSubmit={this.onSubmit.bind(this)}>
                        <div >

                            <div style={label_left}>
                                <label form="startDate" >Дата начала:</label>
                                <br/>
                                <input
                                    type = "date"
                                    id = "startDate"
                                    name = "startDate"
                                    required
                                    style={leftInput}
                                    value={this.state.contract.startDate}
                                    onChange={this.startDateHandler.bind()}
                                    onBlur={this.blurHandler}
                                />
                                <NotificationContainer/>
                            </div>

                            <div style={label_right}>
                                <label form="endDate" >Дата окончания:</label>
                                <br/>
                                <input
                                    type = "date"
                                    id = "endDate"
                                    name = "endDate"
                                    required
                                    style={rightInput}
                                    value={this.state.contract.endDate}
                                    onChange={this.endDateHandler}
                                    onBlur={this.blurHandler}
                                />
                                <NotificationContainer/>
                            </div>
                            <br/>

                            <div style={label_left}>
                                <label form="contractTypes">Вид контракта:</label>
                                <br/>
                                <select style={leftInput} name="contractType" onBlur={this.blurHandler} onChange={this.contractTypeHandler.bind(this)}>
                                    <option></option>
                                    <ContractTypes contractTypes={this.state.contractTypes} contractType={this.state.contract.contractType}/>
                                </select>
                                <NotificationContainer/>
                            </div>


                            <div style={label_right}>
                                <label form="user" >Клиент:</label>
                                <br/>
                                <select style={rightInput} name = "user" onBlur={this.blurHandler}  onChange={this.userHandler.bind(this)}>
                                    <option></option>
                                    <Users users={this.state.users} user = {this.state.contract.user}/>
                                </select>
                                <NotificationContainer/>
                            </div>

                            <br/>
                            <div style={label_left}>
                                <label form="currency" >Вид валюты:</label>
                                <br/>
                                <select style={leftInput} name = "currency" onBlur={this.blurHandler} onChange={this.currencyHandler.bind(this)}>
                                    <option></option>
                                    <Currencies currencies={this.state.currencies} currency={this.state.contract.currency}/>
                                </select>
                                <NotificationContainer/>
                            </div>

                            <div style={label_left}>
                                <label form="sum">Сумма вклада:</label>
                                <br/>
                                <input
                                    type = "number"
                                    name = "sum"
                                    id="sum"
                                    style={leftInput}
                                    value={this.state.contract.sum}
                                    onChange={this.sumHandler.bind(this)}
                                    onBlur={this.blurHandler}
                                />
                                <NotificationContainer/>
                            </div>
                            <br/>

                            <div style={label_left}>
                                <label form="percent">Проценты:</label>
                                <br/>
                                <input
                                    type = "number"
                                    name = "percent"
                                    id="percent"
                                    style={leftInput}
                                    value={this.state.contract.percent}
                                    onChange={this.percentHandler.bind(this)}
                                    onBlur={this.blurHandler}
                                />
                                <NotificationContainer/>
                            </div>
                        </div>
                        <br/>
                        <Button color="primary" type="submit" style={submitStyle} disabled=
                            {  this.state.startDateError
                                || this.state.endDateError
                                || this.state.contractTypeError
                                || this.state.userError
                                || this.state.currencyError
                                || this.state.sumError
                                || this.state.percentError
                            }>Save</Button>{' '}
                        <Button color="secondary" style={submitStyle} ><Link to="/agreements">Cancel</Link></Button>
                        <br/>
                    </form>
                </Container>
            </div>
        )
    }
}

const submitStyle = {
    flex:'5',
    padding:'5px',
    margin:'50px 0px 0px 0px',
    width:'20%'
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


export default withRouter(CreateContract);