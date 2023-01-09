import React, {Component, useCallback} from "react";
import ReactDOM from "react-dom";
import {BrowserRouter, BrowserRouter as Router, HashRouter, Route, Switch} from 'react-router-dom';
import UserInfo from "../../../frontend/src/components/UserInfo";
import RegisterUser from "../../../frontend/src/components/RegisterUser";
import 'bootstrap/dist/css/bootstrap.min.css';
import CreateContract from "../../../frontend/src/components/CreateContract";
import ContractInfo from "../../../frontend/src/components/ContractInfo";
import AccountsInfo from "../../../frontend/src/components/AccountsInfo";
import ContractOperationsInfo from "../../../frontend/src/components/ContractOperationsInfo";
import Cards from "../../../frontend/src/components/Cards";
import ATM from "../../../frontend/src/components/ATM";
import ATM_PIN from "../../../frontend/src/components/ATM_PIN";
import ATM_Operations from "../../../frontend/src/components/ATM_Operations";
import ATM_Operations_Remainder from "../../../frontend/src/components/ATM_Operations_Remainder";
import ATM_Operations_Withdraw from "../../../frontend/src/components/ATM_Operation_Withdraw";
import ATM_Operations_Mobile from "../../../frontend/src/components/ATM_Operations_Mobile";
import ATM_Operations_MobilePayment from "../../../frontend/src/components/ATM_Operations_MobilePayment";
import ATM_Operations_MobilePaymentPay from "../../../frontend/src/components/ATM_Operations_MobilePaymentPay";


export class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <HashRouter>
                <Switch>
                    <Route exact path='/'  component={UserInfo}/>
                    <Route exact path='/clients/:id' component={RegisterUser}/>
                    <Route exact path='/agreements/:id' component={CreateContract}/>
                    <Route exact path='/agreements/' component={ContractInfo}/>
                    <Route exact path='/accounts/' component={AccountsInfo}/>
                    <Route exact path='/accounts/:id' component={AccountsInfo}/>
                    <Route exact path='/operations/' component={ContractOperationsInfo}/>
                    <Route exact path='/agreements/details/:id' component={ContractOperationsInfo}/>
                    <Route exact path='/cards/' component={Cards}/>
                    <Route exact path='/ATM/Operations/Mobile' component={ATM_Operations_Mobile}/>
                    <Route exact path='/ATM/Operations/Mobile/Payment/Pay' component={ATM_Operations_MobilePaymentPay}/>
                    <Route exact path='/ATM/Operations/Mobile/Payment' component={ATM_Operations_MobilePayment}/>
                    <Route exact path='/ATM/Operations/Withdraw' component={ATM_Operations_Withdraw}/>
                    <Route exact path='/ATM/Operations/Remainder' component={ATM_Operations_Remainder}/>
                    <Route exact path='/ATM/Operations/' component={ATM_Operations}/>
                    <Route exact path='/ATM/:id' component={ATM_PIN}/>
                    <Route exact path='/ATM/' component={ATM}/>
                </Switch>
            </HashRouter>
        );
    }
}
export default App;

ReactDOM.render(<App />, document.querySelector("#app"));