import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FormAlert from "./FormAlert.js";
import {persistor} from "../redux/store";

export default class AddBankAccounts extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            request: {
                routingNumber: 0,
                accountNumber: 0,
                nicname: "",
                balance: 1000,
            },
            respMessage: {
                messageType: "NONE",
                message: "",
            },
            showMessage: false,
        };
    }

    handleChange = (event) => {
        var newRequest = this.state.request;
        newRequest[event.target.id] = event.target.value;
        this.setState((state) => ({request: newRequest}));
    };

    setMessage(message, messageType) {
        var newRespMessage = this.state.respMessage;
        newRespMessage.message = message;
        newRespMessage.messageType = messageType;
        this.setState((state) => ({
            respMessage: newRespMessage,
            showMessage: true,
        }));
    }

    sendRequest = (event) => {
        event.preventDefault();
        const path = this.props.domainName + "/bank/createBankAccount";
        fetch(path, {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify(this.state.request),
        })
            .then((res) => {
                if (res.status === 302) {
                    persistor.purge();
                }
                return res.json();
            })
            .then(
                (result) => {
                    if (result.error != null) {
                        console.log(result.error);
                        this.setMessage(result.error.message, "ERROR");
                    } else {
                        this.setMessage("Successfully Created a Bank Account!", "SUCCESS");
                    }
                },
                (error) => {
                    this.setMessage("ERROR sending request", "ERROR");
                }
            );
    };

    render() {
        const formEntries = [
            {
                name: "Routing Number",
                valKey: "routingNumber",
            },
            {
                name: "Account Number",
                valKey: "accountNumber",
            },
            {
                name: "Nickname",
                valKey: "nickname",
            },
        ];
        let formBlocks = formEntries.map((formEntry, index) => {
            return (
                <Form.Group controlId={formEntry.valKey} key={formEntry.valKey}>
                    <Form.Label style={{fontFamily: "Muli"}} column="lg">
                        {formEntry.name}
                    </Form.Label>
                    <Form.Control
                        required
                        style={{fontFamily: "Muli"}}
                        size="lg"
                        type={formEntry.name === "Nickname" ? "text" : "number"}
                        step="1"
                        min="0"
                        placeholder={"Enter " + formEntry.name}
                        onChange={this.handleChange}
                    />
                </Form.Group>
            );
        });
        return (
            <>
                <FormAlert
                    onClose={() => {
                        this.setState((state) => ({
                            showMessage: false,
                        }));
                    }}
                    showMessage={this.state.showMessage}
                    messageType={this.state.respMessage.messageType}
                    message={this.state.respMessage.message}
                />
                <Form onSubmit={this.sendRequest}>
                    {formBlocks}

                    <Button className="submitButton" type="submit">
                        Submit
                    </Button>
                </Form>
            </>
        );
    }
}
