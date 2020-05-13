import React from "react";

const purple = "rgba(102, 0, 153, 1)";
const black = "rgba(0, 0, 0, 1)";
const fontSize = 24;
export default class CFeedItemEmpty extends React.Component {

    render() {

        return (
            <div                
            >
                <div

                    style={{
                        display: "flex",
                        color: black,
                        fontSize: fontSize,
                        height: "150px",
                        alignItems: "center",
                        justifyContent: "center",
                        textAlign: "center"
                    }} className="textStyle"
                >
                    Hi, I'm empty!
                </div>
                <hr
                    style={{
                        color: purple,
                        backgroundColor: purple,
                    }}
                />
            </div>
        );


    }
}
