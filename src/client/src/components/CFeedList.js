import React, { PureComponent } from "react";
import CFeedItem from "./CFeedItem.js";
import { fetchFeed } from "../functions/fetchFeed";
import debounce from "lodash.debounce";

export default class CFeedList extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      num_items: 30,
      feedItemDatas: [],
      userId: this.props.userId,
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
      case "Friends":
        fetch_type = "Friend";
        break;
      case "Public":
        fetch_type = "Public";
        break;
    }
    fetchFeed(this.state.userId, num_items, fetch_type)
      .then((response) => response.json())
      .then((response) => {
        console.log(response);
        if (response.data) {
          // const feedItemDatas = response.data.filter(d => d.type === fetch_type.toUpperCase());
          const feedItemDatas = response.data.filter((d) => d.type !== "IN");
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

    // window.onscroll = debounce(() => {
    //     const updateList,
    //     state: {
    //         error,
    //         isLoading,
    //         hasMore,
    //     },
    // })

    render() {
        const payments = this.state.feedItemDatas;
        const feedItems = [];
        if (payments) {
            for (let ii = 1; ii <= payments.length; ii++) {
                var payment = payments[ii-1];
                if (payment.fromUser === undefined) {
                    var fromUserHandle = "{error getting sender}";
                }
                if (payment.toUser === undefined) {
                    var toUserHandle = "{error getting recipient}";
                }
                
                if (payment.fromUser !== undefined && payment.toUser !== undefined) {
                    var fromUserHandle = payment.fromUser.handle;
                    var toUserHandle = payment.toUser.handle;    
                }

                var paymentType = payment.type===undefined ? "{error getting payment type}" : payment.type;

                var paymentAmount = payment.amount === undefined ? "{error getting payment amount}" : payment.amount;

                var timestamp = payment.timestamp === undefined ? "{error getting payment timestamp}" : payment.timestamp;
                feedItems.push(
                    <CFeedItem
                        tab={this.props.feedTab}
                        listIndex={ii}
                        key={ii.toString()}
                        name1={fromUserHandle}
                        name2={toUserHandle}
                        message={paymentType}
                        paymentAmount={paymentAmount}
                        itemDateTime={timestamp}/>
                );
            }
        }

  render() {
    const payments = this.state.feedItemDatas;
    const feedItems = [];
    for (let ii = 1; ii <= payments.length; ii++) {
      var payment = payments[ii - 1];
      feedItems.push(
        <CFeedItem
          tab={this.props.feedTab}
          listIndex={ii}
          key={ii.toString()}
          name1={payment.fromUser.handle}
          name2={payment.toUser.handle}
          message={payment.type}
          paymentAmount={payment.amount}
          itemDateTime={payment.timestamp}
        />
      );
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
