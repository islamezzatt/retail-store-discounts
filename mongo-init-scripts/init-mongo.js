db = db.getSiblingDB('retaildb');
db.createCollection('users');
db.users.insertMany([
    {
        username: 'testEmployee',
        password: '$2a$10$9L2aW0GbB.jdKYb0FAyygORT/xkSUZX3ORjTdUdY8.xA3DUYX97je',
        userType: 'EMPLOEE',
        userCreationDate: new Date()
    },
    {
        username: 'testAffiliate',
        password: '$2a$10$9L2aW0GbB.jdKYb0FAyygORT/xkSUZX3ORjTdUdY8.xA3DUYX97je',
        userType: 'AFFILIATE',
        userCreationDate: new Date()
    },
	{
        username: 'testLoyal',
        password: '$2a$10$9L2aW0GbB.jdKYb0FAyygORT/xkSUZX3ORjTdUdY8.xA3DUYX97je',
        userType: 'REGULAR',
        userCreationDate: 2020-11-08T21:00:00.000+00:00
    }
]);