package beer.hoppyhour.api.model;

public enum ERole {
    //The standard user. They can perform crud on their own account info, recipes, etc. They are not able to add new ingredients or other constant values. 
    ROLE_USER,
    //The expert is a user that we trust to add ingredients and other constant rows to the database. We believe that they have enough integrity to know about hops and such.
    ROLE_EXPERT,
    //The moderator can untrust a user. This will hide their recipes, comments, replies, and other ingredient posts from public view.
    ROLE_MODERATOR,
    //The admin can perform crud operations on all tables. BEWARE!
    ROLE_ADMIN
}
