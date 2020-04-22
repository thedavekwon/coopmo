import React from "react";
import Select from 'react-select';

export default class CDropdown extends React.Component {
    constructor(props) {
        super(props);
    }

    handleChange = (event) => {
        this.props.handleChange(event.value);
    };

    render() {
        return (
            <div style={{backgroundColor: "rgba(0, 0, 0, 0)", overflowY: "visible"}}>
                <div>
                    <div style={{zIndex: 9}} className="outerDiv centerer">
                        <div
                            id="38:1017"
                            style={{
                                marginLeft: "3%",
                                marginRight: "0%",
                                flexGrow: 1,
                                height: "35%",
                                top: "40%",
                                width: "100%",
                                color: "rgba(38, 38, 38, 1)",
                                fontSize: 16,
                                fontWeight: 400,
                                fontFamily: "Muli",
                                textAlign: "LEFT",
                                fontStyle: "normal",
                                lineHeight: "175%",
                                letterSpacing: "0px",
                                overflowY: "visible",
                            }}
                            className="innerDiv"
                        >
                            <div>
                <span style={{}} key="end">

                    <Select isSearchable={true} onChange={this.handleChange} options={this.props.list}
                            placeholder='Search...'/>
                </span>
                            </div>
                        </div>
                    </div>
                    <div style={{zIndex: 3}} className="outerDiv centerer">
                        <div
                            id="38:1022"
                            style={{
                                marginLeft: "3%",
                                width: "100%",
                                height: 22,
                                top: "16%",
                                color: "rgba(38, 38, 38, 1)",
                                fontSize: 18,
                                fontWeight: 700,
                                fontFamily: "Muli",
                                textAlign: "LEFT",
                                fontStyle: "normal",
                                lineHeight: "125%",
                                letterSpacing: "0px",
                            }}
                            className="innerDiv"
                        >
                            <div>
                <span style={{}} key="end">
                  {this.props.name}
                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
