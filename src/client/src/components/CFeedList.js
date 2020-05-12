import React from "react";
import CFeedItemPayment from "./CFeedItemPayment.js";
import CFeedItemBank from "./CFeedItemBank.js";
import {fetchFeed} from "../functions/fetchFeed";

export default class CFeedList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      num_items: 30,
      feedItemDatas: [],
    };
  }

  // each payment item has following:
  // id, amount, type, timestamp, fromUser, toUser, likes

  updateList(feedTab) {
    const num_items = this.state.num_items;
    var fetch_type = "";
    switch (feedTab) {
      case "Me":
        fetch_type = "Private";
        break;
      case "Friend":
        fetch_type = "Friend";
        break;
      case "Public":
        fetch_type = "Public";
        break;
      default:
        fetch_type = "(ERROR, SHOULD NOT SEE THIS)";
    }
    fetchFeed(this.props.domainName, num_items, fetch_type)
      .then((response) => response.json())
      .then((response) => {
        console.log(response);
        if (response.data) {
          // const feedItemDatas = response.data.filter(d => d.type === fetch_type.toUpperCase());
          const feedItemDatas = response.data;
          this.setState({
            feedItemDatas: this.state.feedItemDatas.concat(feedItemDatas),
          });
        }
      });
  }

  componentDidMount() {
    this.updateList(this.props.feedTab);
  }

  componentDidUpdate(prevProps) {
    if (this.props.feedTab !== prevProps.feedTab) {
      this.setState({
        feedItemDatas: [],
      });
      this.updateList(this.props.feedTab);
    }
  }

  render() {
    const payments = this.state.feedItemDatas;
    const feedItems = [];
    for (let ii = 1; ii <= payments.length; ii++) {
      var payment = payments[ii - 1];
      if (payment.type === "IN" || payment.type === "OUT") {
        feedItems.push(
            <CFeedItemBank
                tab={this.props.feedTab}
                listIndex={ii}
                key={ii.toString()}
                type={payment.type}
                amount={payment.amount}
                timestamp={payment.timestamp}
            />
        );
      } else {
        feedItems.push(
          <CFeedItemPayment
            tab={this.props.feedTab}
            listIndex={ii}
            domainName={this.props.domainName}
            key={ii.toString()}
            fromUserId={payment.fromUser.id}
            fromUserHandle={payment.fromUser.handle}
            toUserHandle={payment.toUser.handle}
            type={payment.type}
            amount={payment.amount}
            timestamp={payment.timestamp}
            comment={payment.comment}
          />
        );
      }
    }

    return (
      <div
        style={{
          overflowY: "scroll",
          maxHeight: "600px",
        }}
      >
        {feedItems}
      </div>
    );
  }
}
