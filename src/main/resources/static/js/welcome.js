greetUser();

function greetUser() {
    if (!sessionStorage.getItem('userName') || sessionStorage.getItem("userName") === "null") {
        let name = prompt("Please Enter Your Name");
        if (name == '' || name === null) {
            let newName = prompt("please type a name with at least one character")
            sessionStorage.setItem("userName", newName);
            createUser(newName);

        } else {
            sessionStorage.setItem("userName", name);
            createUser(name);
        }
    }
}

setInterval(check, 1000);

function check() {
    if (!sessionStorage.getItem("userName")) {
        window.location.replace("http://localhost:8080/welcome")
    }
}


async function createUser(name) {
    const user = {
        'name': name
    }
    try {
        let responseEntity = await fetch('/users/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
        let returnedUser = await responseEntity.json();
        await sessionStorage.setItem("user", JSON.stringify(returnedUser));
        await window.location.replace("http://localhost:8080/welcome")
    } catch (error) {
        console.log(error);
    }
}

const createChannelButton = document.getElementById('create-channel');
createChannelButton.addEventListener('click', createNewChannel)

function createNewChannel() {
    let channelName = prompt("Please Input A Channel Name");
    if (channelName === null || channelName === '') {
        channelName = prompt("Please Type A Channel Name With At least 1 character");
    }
    const channel = {
        "channelName": channelName
    }
    fetch('/channels/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(channel)
    })
        .then(response => response.json())
        .then(channel => {
            console.log(channel);
            if (channel.channelId != null) {
                location.reload()
            } else {
                alert("There was an error creating your channel")
                return window.location.replace("http://localhost:8080/welcome")
            }
        })
        .catch(error => console.log("there was an error creating a channel", error));
}
