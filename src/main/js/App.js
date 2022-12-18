import React, {Component, useCallback} from "react";
import ReactDOM from "react-dom";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import UserInfo from "../../../frontend/src/components/UserInfo";
import RegisterUser from "../../../frontend/src/components/RegisterUser";
import 'bootstrap/dist/css/bootstrap.min.css';


export class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users:[]
        }
    }

    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact component={UserInfo}/>
                    <Route path='/clients/:id' component={RegisterUser}/>
                </Switch>
            </Router>
        );
    }
}
export default App;

ReactDOM.render(<App />, document.querySelector("#app"));