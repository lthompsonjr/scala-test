package ParentalController

import org.scalatest.FlatSpec
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

import scala.util.{Failure, Try}

class ParentalControlServiceSpec extends FlatSpec with MockitoSugar {

  "A Parental control service " should "validate the parental rating from the movie service when the parental control is equal to the movie rating" in {
    def movieId: String = "10"
    def parentalControlLevel: String = "r18"
    val service = mock[MovieService]
    when(service.getParentalControlLevel(movieId)).thenReturn(Try("r18"))

    def parentalControl: ParentalControlService = new ParentalControlService(movieId, parentalControlLevel, service)
    val result = parentalControl.resolveParentControl()

    assert(result == true)
  }

  it should "validate the parental rating from the movie service when the parental control is more than the movie rating" in {
    def movieId: String = "10"
    def parentalControlLevel: String = "r18"
    val service = mock[MovieService]
    when(service.getParentalControlLevel(movieId)).thenReturn(Try("U"))

    def parentalControl: ParentalControlService = new ParentalControlService(movieId, parentalControlLevel, service)
    val result = parentalControl.resolveParentControl()

    assert(result == true)
  }

  it should "validate the parental rating from the movie service when the parental control is less than the movie rating" in {
    def movieId: String = "10"
    def parentalControlLevel: String = "U"
    val service = mock[MovieService]
    when(service.getParentalControlLevel(movieId)).thenReturn(Try("r18"))

    def parentalControl: ParentalControlService = new ParentalControlService(movieId, parentalControlLevel, service)
    val result = parentalControl.resolveParentControl()

    assert(result == false)
  }

  it should "validate the parental control level when the input is lowercase" in {
    def movieId: String = "10"
    def parentalControlLevel: String = "u"
    val service = mock[MovieService]

    when(service.getParentalControlLevel(movieId)).thenReturn(Try("r18"))

    def parentalControl: ParentalControlService = new ParentalControlService(movieId, parentalControlLevel, service)
    val result = parentalControl.resolveParentControl()

    assert(result == false)
  }

  it should "be able to handle a failure when movieId isn't available." in {
    def movieId: String = "0"
    def parentalControlLevel: String = "U"

    val service = mock[MovieService]
    when(service.getParentalControlLevel(movieId)).thenReturn(Try(null))

    def parentalControl: ParentalControlService = new ParentalControlService(movieId, parentalControlLevel, service)
    val result = parentalControl.resolveParentControl()

    assert(result == false)
  }

}

class TitleNotFoundException(message: String) extends Exception(message)



