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
                </Switch>
            </HashRouter>
        );
    }
}
export default App;

ReactDOM.render(<App />, document.querySelector("#app"));