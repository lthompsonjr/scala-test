package ParentalController

import scala.util.{Failure, Success, Try}

trait MovieService {
  def getParentalControlLevel(movieId: String): Try[String]
}

class TileNotFoundException(message: String) extends Exception(message)

class ParentalControlService(val movieId: String, val parentalControlLevel: String, val movieService: MovieService) {
  def resolveParentControl(): Boolean = {
    val movieRatingsLevel: String = movieService.getParentalControlLevel(movieId) match {
      case Success(e) => e
      case Failure(f) => "u"
    }

    val boolResult = false
    val parentalControlRating = Ratings.withName(parentalControlLevel.toLowerCase())

    Try(Ratings.withName(movieRatingsLevel.toLowerCase())) match {
      case Success(movieRating) => Ratings.checkRatings(parentalControlRating, movieRating)
      case Failure(f) => false
    }
  }

    object Ratings extends Enumeration {
      val u, pg, r12, r15, r18, na = Value

      def checkRatings(parentalControlRating: Value, movieRating: Value): Boolean = {
        if (parentalControlRating >= movieRating) true else false
      }
    }
}

