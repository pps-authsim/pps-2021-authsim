package it.unibo.authsim.library.user

trait User:
  def username: String
  def password: String

trait UserFactory extends UserIDPolicy with PasswordPolicy with CredentialPolicy with Policy
  //implicit def generate(): User | Option[User] //scala3 union types: return a User OR an Option of User

trait UserBuilder extends UserFactory:
  def generate(username: String, password: String)= Option[User]
//il check delle policy lo gestiamo internamente a generate?

trait UserAutoBuilder extends UserFactory:
  def generate(): User= User("user", "password")
  def times(number: Int): Seq[User]= Seq.fill(number)(generate())
//non sono troppo convinta del nome del metodo

trait UserIDPolicy

trait PasswordPolicy

trait CredentialPolicy

trait Policy

object User:
 def apply(username: String,
           password: String) = new UserImpl(username, password)
 case class UserImpl(username:String, password: String) extends User

 object UserTest extends App:
   import it.unibo.authsim.library.user.User
   val user = User("foo", "foo")
   println(user)
