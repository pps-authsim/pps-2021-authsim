package it.unibo.authsim.library.user

trait User:
  val username: String
  val password: String


trait UserGenerator extends UserBuilder with UserIDPolicy with PasswordPolicy with CredentialPolicy with Policy

trait UserBuilder:
  def generate(user: User)= Option.empty[User]

trait UserAutoBuilder:
  def generate(): User
  def times(number: Int): Seq[User]
//non sono troppo convinta del nome del metodo

trait UserIDPolicy

trait PasswordPolicy

trait CredentialPolicy

trait Policy
