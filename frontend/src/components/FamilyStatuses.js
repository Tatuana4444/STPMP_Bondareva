import React, {Component} from 'react';
import PropTypes from "prop-types";

class FamilyStatuses extends Component{

    constructor(props) {
        super(props);
        this.state = {
            familyStatuses:[],
            familyStatus:''
        }
    }

    render() {
        return (this.props.familyStatuses.map((f) => {
            if(f.name !== this.props.familyStatus) {
                return <option value={f.name} key={f.id}>{f.name}</option>
            }
            else
            {
                return <option value={f.name} key={f.id} selected="selected">{f.name}</option>
            }
        }))
    }
}

//PropTypes
FamilyStatuses.propTypes = {
    familyStatuses:PropTypes.array.isRequired,
    familyStatus:PropTypes.object.isRequired
}
export default FamilyStatuses;