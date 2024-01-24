package net.ddns.raywel.helper

object ConvertMobilePhone {
    private fun isValidPhoneNumber(phone: String?): Boolean {
        // Regular expression to match a phone number format
        // This example allows an optional '+' at the start, followed by digits
        val phoneRegex = Regex("^\\+?[0-9]{5,15}$")

        // Check if the phone number is valid
        return phone?.matches(phoneRegex) ?: false
    }

    fun convertPhoneNumber(phone: String?): String {
        // Validate phone number format
        if (!isValidPhoneNumber(phone)) {
            return ""
        }

        // If the number starts with '+', return as is
        if (phone!!.startsWith("+")) {
            return phone
        }

        // Otherwise, add +66 prefix for Thai numbers
        return "+66${phone.substring(1)}"
    }
}