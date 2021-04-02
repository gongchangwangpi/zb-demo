#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/28 17:53:28

from flask import Flask, render_template
import datetime

app = Flask(__name__)

@app.route("/")
def hello():
    today = datetime.date.today()
    colors = ["red", "yellow", "green", "black", "white"]
    return render_template("index.html", today = today, colors = colors)

@app.route("/user/<name>", methods=["POST", "GET"])
def user(name):
    return "get user: %s"%name

@app.route("/user?name=<name>")
def getUser(name):
    return "get query string user: %s"%name


if __name__ == '__main__':
    app.run(debug=True)
