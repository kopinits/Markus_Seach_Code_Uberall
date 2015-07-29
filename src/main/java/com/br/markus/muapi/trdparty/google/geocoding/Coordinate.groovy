package com.br.markus.muapi.trdparty.google.geocoding

class Coordinate {
	def latitude
	def longitude
	def distance
	def city

	String toString() {
		return latitude + "," + longitude
	}
}
