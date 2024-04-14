// Index.js
//import React from 'react';
//import './index.css'; // Import CSS for styling

const Index = () => {
    return React.createElement(
        'div',
        { className: 'container' },
        React.createElement(
            'h1',
            null,
            'Welcome to My Travel App'
        ),
        React.createElement(
            'div',
            { className: 'buttons' },
            React.createElement('button', { className: 'login-button client' }, 'Login as Client'),
            React.createElement('button', { className: 'login-button agent' }, 'Login as Agent'),
            React.createElement('button', { className: 'register-button' }, 'Register')
        )
    );
};


export default Index;
