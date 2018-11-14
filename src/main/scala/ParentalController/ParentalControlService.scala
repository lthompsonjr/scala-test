package ParentalController

import scala.util.{Failure, Success, Try}

trait MovieService {
  def getParentalControlLevel(movieId: String): Try[String]
}

class ParentalControlService(val movieId: String, val parentalControlLevel: String, val movieService: MovieService) {
  def resolveParentControl(): Boolean = {
    val movieRatingsLevel: String = movieService.getParentalControlLevel(movieId) match {
      case Success(e) => e
      case Failure(f) => s"This  didn't return a movie ID: ${f.getMessage}"
    }

    val parentalControlRating = Ratings.withName(parentalControlLevel.toLowerCase())

    Try(Ratings.withName(movieRatingsLevel.toLowerCase())) match {
      case Success(movieRating) => if (parentalControlRating >= movieRating) true else false
      case Failure(f) => false
    }
  }
}

object Ratings extends Enumeration {
  val u, pg, r12, r15, r18 = Value
}

