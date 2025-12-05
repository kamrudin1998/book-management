db = db.getSiblingDB('bookdb');

// Create collections
db.createCollection('users');
db.createCollection('books');

// Create indexes
db.users.createIndex({ email: 1 }, { unique: true });
db.books.createIndex({ title: 1 });
db.books.createIndex({ author: 1 });

console.log("✅ Database initialized successfully!");
