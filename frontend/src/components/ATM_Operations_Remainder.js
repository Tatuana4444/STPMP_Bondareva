import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import {Button} from "@material-ui/core";
import { saveAs } from "file-saver";

class ATM_Operations_Remainder extends Component{

    constructor(props) {
        super(props);
        if(props.history.location.state !== undefined && props.history.location.state.card !== undefined) {
            this.state = {
                card: props.history.location.state.card,
                money: 0
            };
        }
        else {
            this.state = {
                card: '',
                money: 0
            };
            this.props.history.push('/ATM/')
        }
    }

    componentDidMount() {
        axios.put('/cards/remainder', this.state.card)
            .then(r  => {
                if(r.data >= 0) {
                    this.setState({money: r.data});
                }
                else {
                    this.errorHandler()
                }
            })
            .catch(r =>
            {
                this.errorHandler()
            })
    }

    onBack(e){
        this.props.history.push({pathname: '/ATM/Operations/', state: {card: this.state.card}})
    }

    onCheckDownload(e){
        axios.put('/cards/remainder/download', this.state.card)
            .then(r  => {
                var blob = new Blob([r.data], {type: "text/plain;charset=utf-8"});
                saveAs(blob, "check.txt")
            })
            .catch(r =>
            {
                this.errorHandler()
            })
    }

errorHandler(){
    alert(`Не удалось выполнить соединение с банком. Попробуйте позже`);
    this.props.history.push('/ATM')
}


    render() {

        return (
            <div>
                <AppNavbar/>
                <Container fluid style={label_center}>
                        <h1>Банкомат</h1>
                        <div style={form_cards}>
                            <h3>Ваш остаток</h3>
                            <br/>
                            <h3>{this.state.money}</h3>
                            <Button color="primary" style={margin_cards} onClick={this.onCheckDownload.bind(this)}>Получить чек</Button>
                            <br/>
                            <Button color="primary" style={margin_cards} onClick={this.onBack.bind(this)}>Далее</Button>
                            <br/>
                            <Button color="primary" style={margin_cards}><Link to="/ATM">Вернуть карту</Link></Button>
                        </div>
                </Container>
            </div>
        );
    }
}

const label_center = {
    textAlign: 'center'
}

const form_cards = {
    marginTop: '50px'
}
const margin_cards = {
    marginTop: '20px'
}
export  default  withRouter(ATM_Operations_Remainder);