package org.secret_sharing

/**
 * based on https://en.wikipedia.org/wiki/Lagrange_polynomial
 */
private[secret_sharing] object LaGrangeInterpolation {

  /**
   * gets the value of the 0 coefficient from the polynomial
   * -> secret
   */
  def coefficient0(shares: List[Share]): BigInt = {
    val (xShares, yShares) = shares.map(share => (share.x, share.y)).toVector.unzip
    val prime = BigInt(shares.head.primeUsed)

    shares.indices.foldLeft(BigInt(0)) { case (sum, i) =>
      val (numerator, denum) = shares.indices.foldLeft(BigInt(1), BigInt(1)) { case ((num, den), j) =>
        if (i != j) (num * (0 - xShares(j))).mod(prime) -> (den * (xShares(i) - xShares(j))).mod(prime)
        else num -> den
      }
      (prime + sum + yShares(i) * numerator * modInverse(denum, prime)) % prime
    }
  }

  /**
   * see https://en.wikipedia.org/wiki/Shamir's_Secret_Sharing#Javascript_example
   */
 def gcdD(a: BigInt, b: BigInt): List[BigInt] =
    if(b == 0) List(a, BigInt(1), BigInt(0))
    else {
      val div = a / b
      val rest = a % b
      val gcdDecomposed = gcdD(b, rest)
      List(gcdDecomposed.head, gcdDecomposed(2), gcdDecomposed(1) - gcdDecomposed(2) * div)
    }

  private def modInverse(k: BigInt, prime: BigInt): BigInt = {
    val m = k % prime
    val decompositionOfGcd = if(m < 0) -gcdD(prime, -m)(2) else gcdD(prime, m)(2)
    (prime + decompositionOfGcd) % prime
  }
}