package tinga.nlp.texttools


import Tokenizer.splitToWords

object FeatureExtractor{
  def ngrams(text: String, n: Int = 1): List[String] = {
    def group(splittedText: List[String]): List[List[String]] = splittedText match{
      case(head :: tail) if(splittedText.length >= n) => splittedText.take(n) :: group(tail)
      case(_) => Nil
    }
    group(splitToWords(text).toList) map (x => x.mkString(" "))
  }

  def skipgrams(text: String, n: Int, k: Int): List[String] = {
    val m = splitToWords(text).zipWithIndex.groupBy(x => x._2%k)
    m.mapValues(x => ngrams(x.unzip._1.mkString(" "), n)).toList.flatMap(t => t._2) ::: ngrams(text, n)
  }

  def vocabulary(text: String): List[String] = splitToWords(text).toList

  def absoluteFreq(l: List[Any]): Map[Any, Double] = l.groupBy(identity).mapValues(_.size).mapValues(_.toDouble)

  def relativeFreq(l: List[Any]): Map[Any, Double] = {
    val m = absoluteFreq(l)
    m.mapValues(x => x/m.foldLeft(0.0)(_+_._2))
  }

  def topN(m: Map[Any, Double], n: Int): List[(Any, Double)] = {
    val sorted = m.toList.sortBy(_._2)
    sorted.reverse.take(n)
  }

  def bottomN(m: Map[Any, Double], n: Int): List[(Any, Double)] = {
    val sorted = m.toList.sortBy(_._2)
    sorted.take(n)
  }
}
